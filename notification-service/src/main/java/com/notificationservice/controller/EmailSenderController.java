package com.notificationservice.controller;

import com.notificationservice.dto.UserEvent;
import com.notificationservice.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/email")
public class EmailSenderController {
    private final SimpleEmailService emailService;

    @Autowired
    public EmailSenderController(SimpleEmailService simpleEmailService){
        this.emailService = simpleEmailService;
    }
    @GetMapping
    public void sendEmail(){
        emailService.sendRegistrationEmail(
                new UserEvent((long)3, "FunnyMan", "d.pidluzhnyi@gmail.com"));
    }
}
