package ru.team.up.moderator.schedules;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.service.AssignedEventsServiceImpl;
import ru.team.up.core.service.ModeratorsSessionsServiceImpl;
import ru.team.up.sup.service.ParameterService;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ModeratorsSessionsScheduler {

    private final ModeratorsSessionsServiceImpl moderatorSessionsServiceImpl;
    private final AssignedEventsServiceImpl assignedEventsServiceImpl;

    /**
     * Метод удаляет сессию модератора по активности (удаляет не активных модераторов по полю время прогрева)
     */
//    @Scheduled(fixedRate = 10000)
    @Scheduled(fixedDelayString = "${moderatorActivity.delay}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void removeModeratorSession() {

        log.debug("Получение листа ID неактивных модераторов");
        List<Long> listInactiveModeratorsId = moderatorSessionsServiceImpl
                // Длительность неактивной сессии модератора перед удалением (в минутах) получаемая из СУП
                .getInactiveModerators(LocalDateTime.now().minusMinutes(ParameterService.getModeratorDisconnectTimeout.getValue()));

        if (!listInactiveModeratorsId.isEmpty()) {
            listInactiveModeratorsId
                    .forEach(delete -> {
//                          Сброс всех мероприятий, назначенных на неактивного модератора
                        removeAssignedEventsOnModerator(delete);
//                          Удаление сессии неактивного модератора
                        moderatorSessionsServiceImpl.removeModeratorSession(delete);
                        log.debug("Удалена сессия модератора с id {}", delete);
                    });
        } else {
            log.debug("Неактивных модераторов нет");
        }
    }

    /**
     * Метод удаляет все назначения мероприятий на неактивного модератора перед удалением его сессии
     */
    public void removeAssignedEventsOnModerator(Long id) {
        assignedEventsServiceImpl.getIdAssignedEventsByModeratorId(id)
                .forEach(deleteEvents -> {
                    assignedEventsServiceImpl.removeAssignedEvent(deleteEvents);
                    log.debug("Удалено назначение мероприятия на модератора, id {}", deleteEvents);
                });
    }
}
