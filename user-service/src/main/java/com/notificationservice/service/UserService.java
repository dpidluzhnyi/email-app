package com.notificationservice.service;

import com.notificationservice.dto.UserData;
import com.notificationservice.entity.User;
import com.notificationservice.exception.FailedToCreateUserException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserByUserName(String username);
    List<User> getAllUsers();
    Optional<User> createUser(UserData userData) throws FailedToCreateUserException;
}
