package com.notification.repository;

import com.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByOrderId(String orderId);

    Optional<Notification> findByEventId(String eventId);

    @Query("SELECT n FROM Notification n WHERE " +
            "(:orderId IS NULL OR n.orderId = :orderId)")
    List<Notification> findByOrderIdFilter(@Param("orderId") String orderId);
}
