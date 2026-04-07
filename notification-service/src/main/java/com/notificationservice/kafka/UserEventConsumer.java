package com.notificationservice.kafka;

import com.notificationservice.dto.UserEvent;

public interface UserEventConsumer {
    void handleUserRegistration(UserEvent event);
}
