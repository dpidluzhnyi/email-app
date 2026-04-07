package com.userservice.service;

import com.userservice.dto.UserEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleEmailService {

    private final JavaMailSender mailSender;

    public void sendRegistrationEmail(UserEvent event) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(event.getEmail());
            message.setSubject("Registration Successful");
            message.setText(String.format(
                    "Hello, %s!\n\nYour registration was successful.\n\nYour details:\nEmail: %s\n\nBest regards,\nUser Registration System",
                    event.getFullName(), event.getEmail()
            ));

            mailSender.send(message);
            log.info("Registration email sent to {}", event.getEmail());
        } catch (Exception e) {
            log.info("Failed to send email to {}. Reason: {}", event.getEmail(), e.getMessage());
        }
    }
}