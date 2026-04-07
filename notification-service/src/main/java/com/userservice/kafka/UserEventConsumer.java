package com.userservice.kafka;

import com.userservice.dto.UserEvent;

public interface UserEventConsumer {
    void handleUserRegistration(UserEvent event);
}
