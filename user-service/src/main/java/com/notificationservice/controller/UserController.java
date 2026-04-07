package com.notificationservice.controller;

import com.notificationservice.dto.UserData;
import com.notificationservice.entity.User;
import com.notificationservice.exception.FailedToCreateUserException;
import com.notificationservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserData userData) throws FailedToCreateUserException {
        User user = userService.createUser(userData).orElseThrow();
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }
}
