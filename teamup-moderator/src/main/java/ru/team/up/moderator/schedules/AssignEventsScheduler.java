package ru.team.up.moderator.schedules;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.service.AssignedEventsServiceImpl;
import ru.team.up.core.service.ModeratorsSessionsServiceImpl;
import ru.team.up.sup.service.ParameterService;

import java.util.List;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignEventsScheduler {

    private final AssignedEventsServiceImpl assignedEventsServiceImpl;
    private final ModeratorsSessionsServiceImpl moderatorSessionsServiceImpl;

    /**
     * Метод проверяет по расписанию наличие новых мероприятий в статусе "на проверке"
     * и назначает их на модераторов
     */
//    @Scheduled(fixedRate = 5000)
    @Scheduled(fixedDelayString = "${eventsScan.delay}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void assignEvents() {
        log.debug("Получение списка новых мероприятий");
        List<Long> newEventsIdList = assignedEventsServiceImpl.getIdNotAssignedEvents();

        if (!newEventsIdList.isEmpty()) {

            log.debug("Получение id свободного модератора");
            Long moderatorId = moderatorSessionsServiceImpl.getFreeModeratorWithLimitedEvents(ParameterService.getModeratorEventLimitation.getValue());
            if (moderatorId != null) {

                newEventsIdList.forEach(eventId -> {
                    assignedEventsServiceImpl.saveAssignedEvent(AssignedEvents.builder()
                            .eventId(eventId)
                            .moderatorId(moderatorId)
                            .build());
                });
                log.debug("Новые мероприятия получены и распределены на модераторов");
            } else {
                log.debug("Нет активных модераторов или у них максимальное количество мероприятий");
            }
        } else {
            log.debug("Список новых мероприятий пуст");
        }
    }
}
