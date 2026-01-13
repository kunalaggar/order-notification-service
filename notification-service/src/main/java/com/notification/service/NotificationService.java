package com.notification.service;

import com.notification.entity.Notification;
import com.notification.event.OrderCreatedEvent;
import java.util.List;

public interface NotificationService {

    void processOrderCreatedEvent(OrderCreatedEvent event);

    List<Notification> getAllNotifications();

    Notification getNotificationById(Long id);

    List<Notification> getNotificationsByOrderId(String orderId);
}

