package com.userservice.kafka.impl;

import com.userservice.dto.UserEvent;
import com.userservice.service.SimpleEmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserEventConsumerImplTest {

    @Mock
    private SimpleEmailService emailService;

    @InjectMocks
    private UserEventConsumerImpl consumer;

    @Test
    void kafkaConsumerCallsMailServiceOk() {
        UserEvent event = new UserEvent(1L, "John Doe", "john@example.com");

        consumer.handleUserRegistration(event);

        verify(emailService).sendRegistrationEmail(event);
    }
}
