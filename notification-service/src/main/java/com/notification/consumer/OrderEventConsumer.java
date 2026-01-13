package com.notification.consumer;

import com.notification.event.OrderCreatedEvent;
import com.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "order-created",
            groupId = "notification-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderCreatedEvent(
            OrderCreatedEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            Acknowledgment acknowledgment) {

        log.info("Received OrderCreatedEvent with key: {}, eventId: {}", key, event.getEventId());

        try {
            notificationService.processOrderCreatedEvent(event);
            // Manually acknowledge the message
            acknowledgment.acknowledge();
            log.info("Successfully processed event: {}", event.getEventId());
        } catch (Exception e) {
            log.error("Error processing event {}: {}", event.getEventId(), e.getMessage());
        }
    }
}
