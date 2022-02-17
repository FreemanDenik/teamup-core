package ru.team.up.moderator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.ModeratorsSessions;
import ru.team.up.core.exception.UserNotFoundException;
import ru.team.up.core.repositories.ModeratorsSessionsRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class ModeratorSessionsServiceImpl implements ModeratorSessionsService{

    private ModeratorsSessionsRepository moderatorsSessionsRepository;

    @Autowired
    public ModeratorSessionsServiceImpl(ModeratorsSessionsRepository moderatorsSessionsRepository) {
        this.moderatorsSessionsRepository = moderatorsSessionsRepository;
    }

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
    @Transactional(readOnly = true)
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
     * метод удаления сессии модератора по активности
     * удаляет не активных по полю время прогрева
     * по id
     *
     * @param id
     */

    public void removeModeratorSession(Long id) {
        ModeratorsSessions moderatorsSessions = null;
        if (!moderatorsSessions.createdSessionTime.isEqual(moderatorsSessions.lastUpdateSessionTime)) {
            log.debug("Удаление сессии по ID сессии {}", id);
            moderatorsSessionsRepository.deleteById(id);
        }
    }
}
