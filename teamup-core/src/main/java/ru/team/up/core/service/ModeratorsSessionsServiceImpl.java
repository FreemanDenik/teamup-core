package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.ModeratorSession;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.repositories.ModeratorSessionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Author Mirseit Ibraimov
 * класс управления сессиями модераторов
 */

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModeratorsSessionsServiceImpl implements ModeratorsSessionsService{

    ModeratorSessionRepository moderatorSessionRepository;

    public ModeratorsSessionsServiceImpl() {
    }

    /**
     * Метод получения сессии модератора по ID
     * @param id
     * @return moderatorSession
     */
    public ModeratorSession getModeratorsSession(Long id) {
        log.debug("Получение сессию по ID сессии {}", id);
        return moderatorSessionRepository.getOne(id);
    }

    /**
     * Метод получения всех сессий модератора
     * @param id
     * @return moderatorsSessions
     */
    @Override
    public ModeratorSession getModeratorsSessionByModerator(Long id) {
        ModeratorSession moderatorsSessions = null;
        log.debug("Получение всех сессий");
        for (var moderatorSession : moderatorSessionRepository.findAll()) {
            log.debug("Сравнение ID модератора");
            if (id == moderatorSession.getModeratorId()) {
                moderatorsSessions = Optional.of(moderatorSession).orElseThrow(() -> new UserNotFoundIDException(id));
            }
        }
        log.debug("Получили сессию по ID модератора");
        return moderatorsSessions;
    }

    /**
     * метод создания новой сессии
     * @param id
     * @return
     */
    public ModeratorSession createModeratorsSession(Long id) {
        log.debug("Создание новой сессии c id {}", id);
        ModeratorSession moderatorSession = ModeratorSession.builder()
                .id(id)
                .lastUpdateSessionTime(LocalDateTime.now())
                .createdSessionTime(LocalDateTime.now())
                .build();
        log.debug("Получили новую сессию с id {}", id);
        return moderatorSessionRepository.saveAndFlush(moderatorSession);
    }

    /**
     * метод удаления сессии модератора по id
     * @param id
     */
    public void removeModeratorSession(Long id) {
        log.debug("Удаление сессии по ID сессии {}", id);
        moderatorSessionRepository.deleteById(id);
    }
}
