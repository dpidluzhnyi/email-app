package com.userservice.kafka.impl;

import com.userservice.dto.UserEvent;
import com.userservice.kafka.UserEventProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

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
        kafkaTemplate.send(TOPIC, event.getEmail(), event);
    }
}
