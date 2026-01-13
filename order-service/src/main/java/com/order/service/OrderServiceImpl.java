package com.order.service;

import com.order.entity.Order;
import com.order.event.OrderCreatedEvent;
import com.order.exception.OrderCreationException;
import com.order.model.OrderRequest;
import com.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher publisher;

    @Override
    @Transactional
    public Order createOrder(OrderRequest request) {
        try {
            // Create and save order
            Order order = Order.builder()
                    .customerEmail(request.getCustomerEmail())
                    .productCode(request.getProductCode())
                    .quantity(request.getQuantity())
                    .status("CREATED")
                    .build();

            order = orderRepository.save(order);
            log.info("Order created with ID: {}", order.getId());

            // Create event
            OrderCreatedEvent event = OrderCreatedEvent.builder()
                    .eventId(UUID.randomUUID().toString())
                    .occurredAt(LocalDateTime.now())
                    .orderId(order.getId())
                    .customerEmail(order.getCustomerEmail())
                    .productCode(order.getProductCode())
                    .quantity(order.getQuantity())
                    .build();

            // Publish event
            publisher.publishOrderCreated(event);

            return order;

        } catch (KafkaException e){
            throw new RuntimeException("Kafka publish failed. Order rolled back.");
        } catch (Exception e) {
            log.error("Error creating order", e);
            throw new OrderCreationException("Failed to create order: " + e.getMessage());
        }
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    @Override
    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
