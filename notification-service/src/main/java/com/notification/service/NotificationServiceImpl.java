package com.notification.service;

import com.notification.entity.Notification;
import com.notification.event.OrderCreatedEvent;
import com.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void processOrderCreatedEvent(OrderCreatedEvent event) {
        // Check for duplicate event
        if (notificationRepository.findByEventId(event.getEventId()).isPresent()) {
            log.info("Event {} already processed, skipping", event.getEventId());
            return;
        }

        try {
            // Simulate email sending
            boolean emailSent = simulateEmailSending(event.getCustomerEmail());

            Notification notification = Notification.builder()
                    .eventId(event.getEventId())
                    .orderId(event.getOrderId().toString())
                    .email(event.getCustomerEmail())
                    .type("ORDER_CREATED")
                    .delivered(emailSent)
                    .errorMessage(emailSent ? null : "Failed to send email")
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationRepository.save(notification);
            log.info("Notification created for order {} with status {}",
                    event.getOrderId(), emailSent ? "delivered" : "failed");

        } catch (Exception e) {
            log.error("Error processing event {}", event.getEventId(), e);

            // Store failed notification
            Notification failedNotification = Notification.builder()
                    .eventId(event.getEventId())
                    .orderId(event.getOrderId().toString())
                    .email(event.getCustomerEmail())
                    .type("ORDER_CREATED")
                    .delivered(false)
                    .errorMessage("Processing failed: " + e.getMessage())
                    .createdAt(LocalDateTime.now())
                    .build();

            notificationRepository.save(failedNotification);
        }
    }

    private boolean simulateEmailSending(String email) {
        // Simulate email sending - 90% success rate for demo
        try {
            Thread.sleep(100); // Simulate network delay
            return Math.random() > 0.1; // 90% success rate
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    @Override
    public List<Notification> getNotificationsByOrderId(String orderId) {
        return notificationRepository.findByOrderIdFilter(orderId);
    }
}
