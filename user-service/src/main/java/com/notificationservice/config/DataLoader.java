package com.notificationservice.config;

import com.notificationservice.entity.User;
import com.notificationservice.repository.UserRepository;
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
                new User("alice@example.com", "Alice Black", "55555555555", "alice_black"));
        userRepository.save(
                new User("bob@example.com", "Bob Brown", "44444444444", "bobbrown"));

    }
}
