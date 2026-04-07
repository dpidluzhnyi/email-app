package com.userservice.service;

import com.userservice.dto.UserData;
import com.userservice.entity.User;
import com.userservice.exception.FailedToCreateUserException;
import com.userservice.exception.NoSuchUserException;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserByEmail(String email) throws NoSuchUserException;
    User getUserByUserName(String username) throws NoSuchUserException;
    List<User> getAllUsers();
    Optional<User> createUser(UserData userData) throws FailedToCreateUserException;
}
