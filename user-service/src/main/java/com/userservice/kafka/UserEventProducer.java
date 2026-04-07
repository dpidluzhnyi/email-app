package com.userservice.kafka;

import com.userservice.dto.UserEvent;

public interface UserEventProducer {
    void sendUserRegisteredEvent(UserEvent event);
}
