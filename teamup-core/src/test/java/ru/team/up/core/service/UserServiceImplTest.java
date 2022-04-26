package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.team.up.core.entity.Role;
import ru.team.up.core.entity.User;
import ru.team.up.core.repositories.UserRepository;
import ru.team.up.dto.NotifyDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private NotifyService notifyService;
    @InjectMocks
    private UserService userService = new UserServiceImpl(userRepository, notifyService);

    private User userTest;
    private Set<User> userSetTest;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        userSetTest = new HashSet<>();

        userTest = User.builder()
                .id(1L)
                .email("userka@mail.ru")
                .username("NeoTheUser")
                .firstName("Neo")
                .lastName("Neon")
                .password("Neo12345")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .role(Role.ROLE_USER)
                .subscribers(userSetTest)
                .userMessages(new HashSet<>())
                .build();
    }


    @Test
    void getAllUsers() {
        when(userRepository.findAllUsersByRole(Role.ROLE_USER)).thenReturn(new ArrayList<>());
        userService.getAllUsers();
        verify(userRepository, atLeastOnce()).findAllUsersByRole(Role.ROLE_USER);
    }

    @Test
    void getOneUser() {
        when(userRepository.findUserById(anyLong())).thenReturn(new User());
        userService.getOneUser(1L);
        verify(userRepository, atLeastOnce()).findUserById(1L);
    }

    @Test
    void saveUser() {
        when(userRepository.findUserById(anyLong())).thenReturn(userTest);
        userService.saveUser(userTest);
        verify(userRepository, atLeastOnce()).findUserById(anyLong());
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }

    @Test
    void deleteUser() {
        userService.deleteUser(1L);
        verify(userRepository, atLeastOnce()).deleteById(anyLong());
    }

    @Test
    void findByEmail() {
        when(userRepository.findByEmail("userka@mail.ru")).thenReturn(userTest);
        assertEquals("Neo", userService.findByEmail("userka@mail.ru").get().getFirstName());
    }

    @Test
    void findByUsername() {
        when(userRepository.findByUsername("Neo")).thenReturn(userTest);
        assertEquals("Neon", userService.findByUsername("Neo").get().getLastName());
    }

    @Test
    void getTopUsersInCity() {
        when(userRepository.findUsersByCity(anyString())).thenReturn(new ArrayList<>());
        userService.getTopUsersInCity("Moscow");
        verify(userRepository, atLeastOnce()).findUsersByCity(anyString());
    }
}