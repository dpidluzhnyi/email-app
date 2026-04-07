package com.userservice.config;

import com.userservice.entity.User;
import com.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        userRepository.save(
                new User("alice_black", "Alice Black","alice@example.com",  "55555555555"));
        userRepository.save(
                new User("bobbrown",  "Bob Brown", "bob@example.com", "44444444444"));

    }
}
