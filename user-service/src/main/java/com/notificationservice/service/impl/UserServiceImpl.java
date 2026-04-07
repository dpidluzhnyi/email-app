package com.notificationservice.service.impl;

import com.notificationservice.dto.UserData;
import com.notificationservice.dto.UserEvent;
import com.notificationservice.entity.User;
import com.notificationservice.exception.FailedToCreateUserException;
import com.notificationservice.kafka.impl.UserEventProducerImpl;
import com.notificationservice.repository.UserRepository;
import com.notificationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserEventProducerImpl userEventProducer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserEventProducerImpl userEventProducer) {
        this.userRepository = userRepository;
        this.userEventProducer = userEventProducer;
    }

    @Transactional
    public Optional<User> createUser(UserData userData) throws FailedToCreateUserException {
        User newUser;
        try {
            newUser = userRepository.save(new User(userData));
        } catch (Exception e) {
            throw new FailedToCreateUserException("Unable to create new User");
        }

        UserEvent event = UserEvent.builder()
                .id(newUser.getId())
                .fullName(newUser.getFullName())
                .email(newUser.getEmail())
                .build();
        userEventProducer.sendUserRegisteredEvent(event);

        return Optional.of(newUser);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
