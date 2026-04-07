package com.userservice.repository;

import com.userservice.entity.User;
import com.userservice.exception.FailedToCreateUserException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        savedUser = userRepository.save(
                new User("johndoe", "John Doe", "john@example.com", "1234567890"));
    }

    @Test
    void findByEmailSuccessfully() {
        Optional<User> result = userRepository.findByEmail("john@example.com");

        assertThat(result).isPresent();
    }


    @Test
    void findByUserNameSuccessfully() {
        Optional<User> result = userRepository.findByUserName("johndoe");

        assertThat(result).isPresent();
    }

    @Test
    void userAlreadyExistException() {
        User duplicate = new User("another", "Another User", "john@example.com", "9999999999");

        assertThatThrownBy(() -> {
            userRepository.save(duplicate);
            userRepository.flush();
        }).isInstanceOf(DataIntegrityViolationException.class);
    }

}
