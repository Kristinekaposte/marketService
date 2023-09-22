package com.marketService.notificationservice.business.service;

import com.marketService.notificationservice.event.OrderPlacedEvent;
import com.marketService.notificationservice.model.Notifications;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.List;
import java.util.Optional;

public interface OrderNotificationListenerService {
    List<Notifications> getAllNotifications();

    Optional<Notifications> findNotificationById(String id);

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    void consumeAndSaveNotificationsToDB(OrderPlacedEvent orderPlacedEvent);

    void deleteNotificationById(String id);
}
