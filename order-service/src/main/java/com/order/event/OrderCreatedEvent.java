package com.order.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreatedEvent {

    private String eventId;
    private LocalDateTime occurredAt;
    private Long orderId;
    private String customerEmail;
    private String productCode;
    private Integer quantity;
}
