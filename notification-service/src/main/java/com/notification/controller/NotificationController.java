package com.notification.controller;

import com.notification.entity.CommonResponse;
import com.notification.entity.Notification;
import com.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // Get all notifications
    @GetMapping
    public ResponseEntity<CommonResponse<List<Notification>>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        CommonResponse<List<Notification>> response = new CommonResponse<>(
                true,
                HttpStatus.OK.value(),
                "All notifications fetched successfully",
                notifications,
                null
        );
        return ResponseEntity.ok(response);
    }

    // Get notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<Notification>> getNotificationById(@PathVariable Long id) {
        try {
            Notification notification = notificationService.getNotificationById(id);
            CommonResponse<Notification> response = new CommonResponse<>(
                    true,
                    HttpStatus.OK.value(),
                    "Notification fetched successfully",
                    notification,
                    null
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            CommonResponse<Notification> response = new CommonResponse<>(
                    false,
                    HttpStatus.NOT_FOUND.value(),
                    "Notification not found",
                    null,
                    e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get notifications by orderId
    @GetMapping("/search")
    public ResponseEntity<CommonResponse<List<Notification>>> getNotificationsByOrderId(
            @RequestParam(required = false) String orderId) {
        List<Notification> notifications = notificationService.getNotificationsByOrderId(orderId);
        CommonResponse<List<Notification>> response = new CommonResponse<>(
                true,
                HttpStatus.OK.value(),
                "Notifications fetched successfully",
                notifications,
                null
        );
        return ResponseEntity.ok(response);
    }
}
