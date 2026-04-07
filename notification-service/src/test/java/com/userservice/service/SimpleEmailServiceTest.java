package com.userservice.service;

import com.userservice.dto.UserEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private SimpleEmailService emailService;

    private UserEvent createEvent() {
        return new UserEvent(1L, "John Doe", "john@example.com");
    }

    @Test
    void sendRegistrationEmailOk() {
        emailService.sendRegistrationEmail(createEvent());

        verify(mailSender).send(any(SimpleMailMessage.class));
    }
}
