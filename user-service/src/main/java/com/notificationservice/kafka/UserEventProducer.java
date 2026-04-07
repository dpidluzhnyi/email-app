package com.notificationservice.kafka;

import com.notificationservice.dto.UserEvent;

public interface UserEventProducer {
    void sendUserRegisteredEvent(UserEvent event);
}
