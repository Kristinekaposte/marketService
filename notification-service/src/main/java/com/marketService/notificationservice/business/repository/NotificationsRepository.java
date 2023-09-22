package com.marketService.notificationservice.business.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.marketService.notificationservice.business.repository.model.NotificationsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NotificationsRepository {
    @Autowired
    private DynamoDBMapper dynamoDBMapper;
    public List<NotificationsDAO> getAllNotifications() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        return dynamoDBMapper.scan(NotificationsDAO.class, scanExpression);
    }
    public Optional<NotificationsDAO> getNotificationsById(String id) {
        return Optional.ofNullable(dynamoDBMapper.load(NotificationsDAO.class, id));
    }
    public NotificationsDAO save(NotificationsDAO notificationsDAO) {
        dynamoDBMapper.save(notificationsDAO);
        return notificationsDAO;
    }
    public String delete(String id) {
        NotificationsDAO notif = dynamoDBMapper.load(NotificationsDAO.class, id);
        dynamoDBMapper.delete(notif);
        return "Notification with id: " + id + " deleted";
    }
}
