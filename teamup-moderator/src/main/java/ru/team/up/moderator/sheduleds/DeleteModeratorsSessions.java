package ru.team.up.moderator.sheduleds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.ModeratorsSessions;
import ru.team.up.core.repositories.ModeratorsSessionsRepository;


@Component
@Slf4j
public class DeleteModeratorsSessions {

    private ModeratorsSessionsRepository moderatorsSessionsRepository;

    @Autowired
    public DeleteModeratorsSessions(ModeratorsSessionsRepository moderatorsSessionsRepository) {
        this.moderatorsSessionsRepository = moderatorsSessionsRepository;
    }

    public DeleteModeratorsSessions() {
    }

    /**
     * метод удаления сессии модератора по активности
     * удаляет не активных по полю время прогрева
     * по id
     *
     * @param id
     */
    @Scheduled(fixedDelayString = "${moderatorActivity.delay}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void removeModeratorSession(Long id) {
        log.debug("Удаляем неактивного модератора");
        moderatorsSessionsRepository.removeModeratorSession(id);
        log.debug("Удалили неактивного модератора");
        }
    }