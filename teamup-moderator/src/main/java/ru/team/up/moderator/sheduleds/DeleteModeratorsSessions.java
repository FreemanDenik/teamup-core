package ru.team.up.moderator.sheduleds;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.repositories.AssignedEventsRepository;
import ru.team.up.core.repositories.ModeratorSessionRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeleteModeratorsSessions {

    private ModeratorSessionRepository moderatorSessionRepository;
    private AssignedEventsRepository assignedEventsRepository;

    public DeleteModeratorsSessions() {
    }

    /**
     * метод удаления сессии модератора по активности
     * удаляет не активных по полю время прогрева
     */
//    @Scheduled(fixedDelayString = "${moderatorActivity.delay}")
    @Scheduled(fixedRate = 20000)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void removeModeratorSession() {
        //TODO добавить логику проверки
        log.debug("Удаляем неактивного модератора");
        // Удалить все сессии, у которых последнее время обновления больше заданного значения
        moderatorSessionRepository.getInvalidateSession(LocalDateTime.now().minusMinutes(30L),
                                                        LocalDateTime.now().plusMinutes(30L))
                .forEach(v -> {
                    checkAssignEvent(v.getModeratorId())
                            .forEach(a -> {
                                assignedEventsRepository.updateEventIdBeforeDeleting(1L, a.getEventId());
                                assignedEventsRepository.deleteById(a.getId());
                            });
                    moderatorSessionRepository.deleteById(v.getId());
                });
        // moderatorSessionRepository.deleteById(id);
        log.debug("Удалили неактивного модератора");
    }

    private List<AssignedEvents> checkAssignEvent(Long moderatorId) {
        return assignedEventsRepository.getAllEventsByModeratorId(moderatorId);
    }
}
