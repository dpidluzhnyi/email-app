package com.notificationservice.kafka.impl;

import com.notificationservice.dto.UserEvent;
import com.notificationservice.kafka.UserEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserEventProducerImpl implements UserEventProducer {

    private static final String TOPIC = "user-registration";

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserEventProducerImpl(KafkaTemplate<String, UserEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserRegisteredEvent(UserEvent event) {
        log.info("Sending user registration event to Kafka: {}", event.getEmail());
        CompletableFuture<SendResult<String, UserEvent>> future = kafkaTemplate.send(TOPIC, event.getEmail(), event);
        CompletableFuture<SendResult<String, UserEvent>> sendResultCompletableFuture = future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + event.getEmail() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        event.getEmail() + "] due to : " + ex.getMessage());
            }
        });

    }
}
