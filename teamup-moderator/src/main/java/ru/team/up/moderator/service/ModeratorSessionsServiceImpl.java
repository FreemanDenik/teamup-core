package ru.team.up.moderator.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.ModeratorSession;
import ru.team.up.core.repositories.ModeratorSessionRepository;
import ru.team.up.moderator.exception.ModeratorSessionNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModeratorSessionsServiceImpl implements ModeratorSessionsService {

    private ModeratorSessionRepository moderatorSessionRepository;


    public Optional<ModeratorSession> getModeratorsSession(Long id) {
        log.debug("Получение сессию по ID сессии {}", id);
        return moderatorSessionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public ModeratorSession getModeratorsSessionByModeratorId(Long moderatorId) {
        log.debug("Получен запрос на сессию модератора: {}", moderatorId);
        ModeratorSession moderatorSession = moderatorSessionRepository.findModeratorSessionByModeratorId(moderatorId)
                .orElseThrow(() -> new ModeratorSessionNotFoundException(moderatorId));
        log.debug("Получили сессию по ID: {} модератора: {}", moderatorId, moderatorSession);
        return moderatorSession;
    }

    @Transactional
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

    @Transactional
    public void removeModeratorSession(Long id) {
        ModeratorSession moderatorSession = null;
        if (!moderatorSession.createdSessionTime.isEqual(moderatorSession.lastUpdateSessionTime)) {
            log.debug("Удаление сессии по ID сессии {}", id);
            moderatorSessionRepository.deleteById(id);
        }
    }
}
