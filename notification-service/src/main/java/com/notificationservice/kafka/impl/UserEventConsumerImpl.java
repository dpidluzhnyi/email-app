package com.notificationservice.kafka.impl;

import com.notificationservice.dto.UserEvent;
import com.notificationservice.kafka.UserEventConsumer;
import com.notificationservice.service.SimpleEmailService;
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