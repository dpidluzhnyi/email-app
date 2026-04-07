package com.userservice.service.impl;

import com.userservice.dto.UserData;
import com.userservice.dto.UserEvent;
import com.userservice.entity.User;
import com.userservice.exception.FailedToCreateUserException;
import com.userservice.exception.NoSuchUserException;
import com.userservice.kafka.UserEventProducer;
import com.userservice.kafka.impl.UserEventProducerImpl;
import com.userservice.repository.UserRepository;
import com.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserEventProducer userEventProducer;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserEventProducer userEventProducer) {
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
    public User getUserByEmail(String email) throws NoSuchUserException{
        User user;
        try {
            user = userRepository.findByEmail(email).orElseThrow();
        } catch (Exception e) {
            throw new NoSuchUserException("No user found with specified email");
        }
        return user;
    }

    @Override
    public User getUserByUserName(String username) throws NoSuchUserException {
        User user;
        try {
            user = userRepository.findByUserName(username).orElseThrow();
        } catch (Exception e) {
            throw new NoSuchUserException("No user found with specified email");
        }
        return user;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
