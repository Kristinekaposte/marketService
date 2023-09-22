package com.marketService.notificationservice.business.mappers;

import com.marketService.notificationservice.business.repository.model.NotificationsDAO;
import com.marketService.notificationservice.model.Notifications;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NotificationsMapper {
    NotificationsDAO notificationsToDAO(Notifications notifications);

    Notifications daoToNotifications(NotificationsDAO notificationsDAO);
}
