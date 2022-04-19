package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.ModeratorSession;
import ru.team.up.core.entity.Role;
import ru.team.up.core.repositories.ModeratorSessionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Yura Katkov
 */
@Slf4j
@RunWith(MockitoJUnitRunner.class)
class ModeratorsSessionsServiceImplTest {

    @Mock
    private ModeratorSessionRepository moderatorSessionRepository;

    @InjectMocks
    private ModeratorsSessionsService moderatorsSessionsService =
            new ModeratorsSessionsServiceImpl(moderatorSessionRepository);

    private ModeratorSession moderatorSession;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        moderatorSession = ModeratorSession.builder()
                .id(1L)
                .moderatorId(1L)
                .amountOfModeratorsEvents(5L)
                .createdSessionTime(LocalDateTime.now())
                .lastUpdateSessionTime(LocalDateTime.now())
                .build();
    }

    @Test
    void getModeratorsSession() {
        when(moderatorSessionRepository.getOne(1L)).thenReturn(moderatorSession);
        assertEquals(moderatorSession, moderatorsSessionsService.getModeratorsSession(1L));
    }

    // TODO Вероятно, там должен возвращаться List? Странный метод. Аналог есть ниже, там получаем одну сессию по ID
    @Test
    void getModeratorsSessionByModerator() {
        // Оставлю без теста. Ассерт ниже - пустышка, чтобы тест падал и не забыли переделать.
        // Там через for, плюс само название метода надо менять... В описание сказано, что вернутся все сессии по ID
        assertNotNull(moderatorsSessionsService.getModeratorsSessionByModerator(1L));
    }

    // TODO Там в билдере скорее всего должен быть .moderatorId(id), a не .id(id)
    // Зачем в параметрах id сессии, если мы его должны получить ПОСЛЕ сохранения? Тест рабочий, но метод странный
    @Test
    void createModeratorsSession() {
        when(moderatorSessionRepository.saveAndFlush(any(ModeratorSession.class))).thenReturn(moderatorSession);
        assertEquals(moderatorSession, moderatorsSessionsService.createModeratorsSession(1L));
    }

    // Тут простой метод с return, теста не будет. Но там, кажется, возвращается List<Long>, в котором id сессий
    @Test
    void getInactiveModerators() {
    }

    @Test
    void findModeratorSessionByModeratorId() {
        when(moderatorSessionRepository.findModeratorSessionByModeratorId(1L)).thenReturn(moderatorSession);
        assertEquals(moderatorSession, moderatorsSessionsService.findModeratorSessionByModeratorId(1L));
    }

    @Test
    void incrementModeratorEventCounter() {
        when(moderatorSessionRepository.findModeratorSessionByModeratorId(1L)).thenReturn(moderatorSession);
        when(moderatorSessionRepository.getOne(1L)).thenReturn(moderatorSession);
        // Было 5
        assertEquals(5L, moderatorsSessionsService.getModeratorsSession(1L).getAmountOfModeratorsEvents());
        moderatorsSessionsService.incrementModeratorEventCounter(1L);
        // 5 и осталось... (должно быть 6)
        assertEquals(6L, moderatorsSessionsService.getModeratorsSession(1L).getAmountOfModeratorsEvents());
    }
}