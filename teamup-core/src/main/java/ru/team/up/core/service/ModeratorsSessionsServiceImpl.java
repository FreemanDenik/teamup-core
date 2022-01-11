package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.ModeratorsSessions;
import ru.team.up.core.exception.UserNotFoundException;
import ru.team.up.core.repositories.ModeratorsSessionsRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModeratorsSessionsServiceImpl implements ModeratorsSessionsService{

    ModeratorsSessionsRepository moderatorsSessionsRepository;

    public ModeratorsSessionsServiceImpl() {

    }

    public ModeratorsSessions getModeratorsSession(Long id) {
        log.debug("Получение сессию по ID сессии {}", id);
        return moderatorsSessionsRepository.getOne(id);
    }

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

    public ModeratorsSessions saveModeratorsSession(Long id, LocalDateTime localDateTime, LocalDate localDate) {
        log.debug("Создание новой сессии");
        ModeratorsSessions moderatorsSessions = new ModeratorsSessions();
        moderatorsSessions.setModeratorId(id);
        moderatorsSessions.setCreatedDateTime(localDateTime);
        moderatorsSessions.setCreatedDate(localDate);
        log.debug("Получили новую сессию");
        return moderatorsSessionsRepository.saveAndFlush(moderatorsSessions);
    }

    public void removeModeratorSession(Long id) {
        log.debug("Удаление сессии по ID сессии {}", id);
        moderatorsSessionsRepository.deleteById(id);
    }
}
