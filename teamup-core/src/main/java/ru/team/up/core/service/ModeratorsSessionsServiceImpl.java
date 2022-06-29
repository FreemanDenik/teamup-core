package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class ModeratorsSessionsServiceImpl implements ModeratorsSessionsService {

    ModeratorSessionRepository moderatorSessionRepository;

    public ModeratorsSessionsServiceImpl() {
    }

    /**
     * Метод получения сессии модератора по ID
     *
     * @param id
     * @return moderatorSession
     */
    public ModeratorSession getModeratorsSession(Long id) {
        log.debug("Получение сессию по ID сессии {}", id);
        return moderatorSessionRepository.getOne(id);
    }

    /**
     * Метод получения всех сессий модератора
     *
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
     *
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
     *
     * @param id
     */
    public void removeModeratorSession(Long id) {
        log.debug("Удаление сессии по ID сессии {}", id);
        moderatorSessionRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Long getFreeModerator() {
        log.debug("Получение свободного модератора");
        return moderatorSessionRepository.getFreeModerator();
    }

    @Transactional
    @Override
    public Long getFreeModeratorWithLimitedEvents(int eventLimitation) {
        log.debug("Получение свободного модератора c максимальным количеством мероприятий = {}", eventLimitation);
        return moderatorSessionRepository.getFreeModeratorWithLimitedEvents(eventLimitation);
    }

    @Transactional
    @Override
    public List<Long> getInactiveModerators(LocalDateTime downtime) {
        log.debug("Получение неактивных модераторов");
        return moderatorSessionRepository.getInactiveModerators(downtime);
    }

    @Transactional
    @Override
    public ModeratorSession findModeratorSessionByModeratorId(Long id) {
        log.debug("Получение сессии модератора по id модератора {}", id);
        return moderatorSessionRepository.findModeratorSessionByModeratorId(id);
    }

    /**
     * Метод для инкрементации счетчика мероприятий модератора при добавлении ему нового мероприятия на проверку
     */
    public void incrementModeratorEventCounter(Long id) {
        log.debug("Обновление счетчика мероприятий модератора c id {}", id);
        ModeratorSession moderatorSession = findModeratorSessionByModeratorId(id);

        moderatorSessionRepository.saveAndFlush(ModeratorSession.builder()
                .id(moderatorSession.getId())
                .moderatorId(moderatorSession.getModeratorId())
                .amountOfModeratorsEvents(moderatorSession.getAmountOfModeratorsEvents()+1)
                .lastUpdateSessionTime(moderatorSession.getLastUpdateSessionTime())
                .createdSessionTime(moderatorSession.getCreatedSessionTime())
                .build());
    }
}
