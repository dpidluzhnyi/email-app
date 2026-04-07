package com.userservice.kafka.impl;

import com.userservice.dto.UserEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserEventProducerImplTest {

    @Mock
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    @InjectMocks
    private UserEventProducerImpl userEventProducer;

    private UserEvent createEvent() {
        return UserEvent.builder()
                .id(1L)
                .fullName("John Doe")
                .email("john@example.com")
                .build();
    }

    @Test
    void sendNewUserRegistrationEventToKafkaSuccessfully() {
        UserEvent event = createEvent();

        userEventProducer.sendUserRegisteredEvent(event);

        verify(kafkaTemplate).send(eq("user-registration"), eq("john@example.com"), eq(event));
    }
}
