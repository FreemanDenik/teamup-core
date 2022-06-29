package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.team.up.core.entity.Role;
import ru.team.up.core.entity.User;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Mock
    private BCryptPasswordEncoder encoder;
    @InjectMocks
    private UserService userService = new UserServiceImpl(userRepository, notifyService, encoder);

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
    void shouldSaveUser() {
        String oldPassword = userTest.getPassword();
        userTest.setLastAccountActivity(null);

        when(userRepository.save(userTest)).thenReturn(userTest);
        userService.saveUser(userTest);
        verify(userRepository, atLeastOnce()).save(any(User.class));
        assertNotEquals(oldPassword, userTest.getPassword());
        assertNotNull(userTest.getLastAccountActivity());
    }

    @Test
    void shouldUpdateUser() {
        // given
        String oldPassword = userTest.getPassword();
        userTest.setLastAccountActivity(null);
        when(userRepository.findUserById(1L)).thenReturn(userTest);
        // when
        userService.updateUser(userTest);
        // then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User captorValue = captor.getValue();
        assertThat(captorValue).isEqualTo(userTest);

        assertNotNull(userTest.getLastAccountActivity());
        assertNotEquals(oldPassword, userTest.getPassword());
    }

    @Test
    void shouldThrowExceptionWhenUpdateUser() {
        // given
        when(userRepository.findUserById(1L)).thenReturn(null);
        // when
        Exception e = assertThrows(UserNotFoundIDException.class,
                () -> {
                    userService.updateUser(userTest);
                });
        // then
        assertTrue(e.getMessage().contains("Пользователь не найден. ID = 1"));
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