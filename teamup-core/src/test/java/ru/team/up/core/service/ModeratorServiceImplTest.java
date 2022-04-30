package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Role;
import ru.team.up.core.repositories.AccountRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Yura Katkov
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
class ModeratorServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BCryptPasswordEncoder encoder;

    @InjectMocks
    private ModeratorService moderatorService = new ModeratorServiceImpl(accountRepository, encoder);

    private Account moderatorTest;
    private List<Account> accountListTest;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        moderatorTest = Account.builder()
                .id(1L)
                .email("moderatorka@mail.ru")
                .username("NeoTheModerator")
                .firstName("Neo")
                .lastName("Neon")
                .password("Neo12345")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .role(Role.ROLE_MODERATOR)
                .build();

        accountListTest = new ArrayList<>();
        accountListTest.add(moderatorTest);
    }

    @Test
    void getAllModerators() {
        when(accountRepository.findAllByRole(Role.ROLE_MODERATOR)).thenReturn(accountListTest);
        assertEquals(moderatorTest, moderatorService.getAllModerators().get(0));
    }

    @Test
    void getOneModerator() {
        when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(moderatorTest));
        assertEquals(moderatorTest, moderatorService.getOneModerator(1L));
    }

    @Test
    void saveModerator() {
        when(accountRepository.save(moderatorTest)).thenReturn(moderatorTest);
        assertEquals(moderatorTest, moderatorService.saveModerator(moderatorTest));
    }

    @Test
    void moderatorIsExistsById() {
        when(accountRepository.existsById(1L)).thenReturn(true);
        assertTrue(moderatorService.moderatorIsExistsById(1L));
        assertFalse(moderatorService.moderatorIsExistsById(2L));
    }
}