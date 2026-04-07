package com.userservice.controller;

import com.userservice.dto.UserData;
import com.userservice.entity.User;
import com.userservice.exception.FailedToCreateUserException;
import com.userservice.exception.NoSuchUserException;
import com.userservice.service.UserService;
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
        User user = userService.createUser(userData);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/search")
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) throws NoSuchUserException {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }
}
