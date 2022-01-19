package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.ModeratorsSessions;
import ru.team.up.core.exception.UserNotFoundException;
import ru.team.up.core.repositories.ModeratorsSessionsRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Author Mirseit Ibraimov
 * класс управления сессиями модераторов
 */

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModeratorsSessionsServiceImpl implements ModeratorsSessionsService{

    ModeratorsSessionsRepository moderatorsSessionsRepository;

    public ModeratorsSessionsServiceImpl() {
    }

    /**
     * Метод получения сессии модератора по ID
     * @param id
     * @return moderatorSession
     */
    public ModeratorsSessions getModeratorsSession(Long id) {
        log.debug("Получение сессию по ID сессии {}", id);
        return moderatorsSessionsRepository.getOne(id);
    }

    /**
     * Метод получения всех сессий модератора
     * @param id
     * @return moderatorsSessions
     */
    @Override
    public ModeratorsSessions getModeratorsSessionByModerator(Long id) {
        ModeratorsSessions moderatorsSessions = null;
        log.debug("Получение всех сессий");
        for (var moderatorSession : moderatorsSessionsRepository.findAll()) {
            log.debug("Сравнение ID модератора");
            if (id == moderatorSession.getModeratorId()) {
                moderatorsSessions = Optional.of(moderatorSession).orElseThrow(() -> new UserNotFoundException(id));
            }
        }
        log.debug("Получили сессию по ID модератора");
        return moderatorsSessions;
    }

    /**
     * метод создания новой сессии
     * @param id
     * @param localDateTime
     * @return
     */
    public ModeratorsSessions createModeratorsSession(Long id, LocalDateTime localDateTime) {
        log.debug("Создание новой сессии c id {}", id);
        ModeratorsSessions moderatorsSessions = new ModeratorsSessions();
        moderatorsSessions.setModeratorId(id);
        moderatorsSessions.setLastUpdateSessionTime(localDateTime);
        moderatorsSessions.setCreatedSessionTime(localDateTime);
        log.debug("Получили новую сессию с id {}", id);
        return moderatorsSessionsRepository.saveAndFlush(moderatorsSessions);
    }

    /**
     * метод удаления сессии модератора по id
     * @param id
     */
    public void removeModeratorSession(Long id) {
        log.debug("Удаление сессии по ID сессии {}", id);
        moderatorsSessionsRepository.deleteById(id);
    }
}
