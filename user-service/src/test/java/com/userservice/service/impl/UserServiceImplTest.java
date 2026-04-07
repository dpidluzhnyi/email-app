package com.userservice.service.impl;

import com.userservice.dto.UserData;
import com.userservice.dto.UserEvent;
import com.userservice.entity.User;
import com.userservice.exception.FailedToCreateUserException;
import com.userservice.exception.NoSuchUserException;
import com.userservice.kafka.UserEventProducer;
import com.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserEventProducer userEventProducer;

    @InjectMocks
    private UserServiceImpl userService;

    private UserData validUserData;
    private User savedUser;

    @BeforeEach
    void setUp() {
        validUserData = new UserData();
        validUserData.setFullName("John Doe");
        validUserData.setEmail("john@example.com");
        validUserData.setPhone("1234567890");
        validUserData.setUserName("johndoe");

        savedUser = new User(1L, "johndoe", "John Doe", "john@example.com", "1234567890");
    }

    @Test
    void createUserAndSaveSuccessfully() throws FailedToCreateUserException {
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        Optional<User> result = userService.createUser(validUserData);

        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getEmail()).isEqualTo("john@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void eventSentToKafkaSuccessfully() throws FailedToCreateUserException {
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        userService.createUser(validUserData);

        verify(userEventProducer).sendUserRegisteredEvent(any(UserEvent.class));
    }

    @Test
    void createUserFails() {
        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("message"));

        assertThatThrownBy(() -> userService.createUser(validUserData))
                .isInstanceOf(FailedToCreateUserException.class)
                .hasMessage("Unable to create new User");
    }

    @Test
    void getUserByEmailSuccessfully() throws NoSuchUserException {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(savedUser));

        User result = userService.getUserByEmail("john@example.com");

        assertThat(result.getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void getUserByEmailFails() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByEmail("unknown@example.com"))
                .isInstanceOf(NoSuchUserException.class)
                .hasMessage("No user found with specified email");
    }

    @Test
    void getUserByUserNameSuccessfully() throws NoSuchUserException {
        when(userRepository.findByUserName("johndoe")).thenReturn(Optional.of(savedUser));

        User result = userService.getUserByUserName("johndoe");

        assertThat(result.getUserName()).isEqualTo("johndoe");
    }

    @Test
    void getUserByUserNameFails() {
        when(userRepository.findByUserName("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getUserByUserName("unknown"))
                .isInstanceOf(NoSuchUserException.class);
    }

    @Test
    void getAllUsersSuccessfully() {
        User user2 = new User(2L, "jane", "Jane Doe", "jane@example.com", "0987654321");
        when(userRepository.findAll()).thenReturn(List.of(savedUser, user2));

        List<User> result = userService.getAllUsers();

        assertThat(result).hasSize(2);
        verify(userRepository).findAll();
    }
}
