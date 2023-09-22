package com.marketService.notificationservice.business.service.impl;

import com.marketService.notificationservice.business.mappers.NotificationsMapper;
import com.marketService.notificationservice.business.repository.NotificationsRepository;
import com.marketService.notificationservice.business.repository.model.NotificationsDAO;
import com.marketService.notificationservice.business.service.OrderNotificationListenerService;
import com.marketService.notificationservice.event.OrderPlacedEvent;
import com.marketService.notificationservice.model.Notifications;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderNotificationListenerServiceImpl implements OrderNotificationListenerService {

    @Autowired
    private NotificationsRepository repository;
    @Autowired
    private NotificationsMapper mapper;

    @Override
    public List<Notifications> getAllNotifications() {
        List<Notifications> list = repository.getAllNotifications()
                .stream()
                .map(mapper::daoToNotifications)
                .collect(Collectors.toList());
        log.info("Size of the Notifications list: {}", list.size());
        return list;
    }

    @Override
    public Optional<Notifications> findNotificationById(String id) {
        Optional<NotificationsDAO> notificationsDAO = repository.getNotificationsById(id);
        if (!notificationsDAO.isPresent()) {
            log.info("Notification with id {} does not exist.", id);
            return Optional.empty();
        }
        log.info("Notification with id {} found.", id);
        return notificationsDAO.map(mapper::daoToNotifications);
    }
    @Override
    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeAndSaveNotificationsToDB(OrderPlacedEvent orderPlacedEvent) {
        log.info(String.format("Received orderPlacedEvent in notification service => %s", orderPlacedEvent.toString()));
        Notifications notifications = new Notifications();
        notifications.setMessage("Received  => " + orderPlacedEvent.toString());
        repository.save(mapper.notificationsToDAO(notifications));
    }

    @Override
    public void deleteNotificationById(String id) {
        repository.delete(id);
        log.info("Notification entry with id: {} is deleted", id);
    }
}

