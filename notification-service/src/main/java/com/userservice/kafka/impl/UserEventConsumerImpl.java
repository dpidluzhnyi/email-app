package com.userservice.kafka.impl;

import com.userservice.dto.UserEvent;
import com.userservice.kafka.UserEventConsumer;
import com.userservice.service.SimpleEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumerImpl implements UserEventConsumer {

    private final SimpleEmailService emailService;

    @KafkaListener(topics = "user-registration", groupId = "notification-group")
    public void handleUserRegistration(UserEvent event) {
        log.info("Received user registration event: {}", event.getEmail());
        emailService.sendRegistrationEmail(event);
    }
}