package ru.team.up.moderator.sheduleds;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.repositories.AssignedEventsRepository;
import ru.team.up.core.repositories.ModeratorSessionRepository;
import ru.team.up.core.service.AssignedEventsServiceImpl;
import ru.team.up.moderator.service.ModeratorSessionsServiceImpl;

import java.util.List;


@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignEvents {

    private AssignedEventsServiceImpl assignedEventsServiceImpl;
    private ModeratorSessionsServiceImpl moderatorSessionsServiceImpl;
    private AssignedEventsRepository assignedEventsRepository;
    private ModeratorSessionRepository moderatorSessionRepository;

    /**
     * Метод проверяет по расписанию наличие новых мероприятий в статусе "на проверке" и назначает их на модераторов
     */
    @Scheduled(fixedRate = 5000)
//    @Scheduled(fixedDelayString = "${eventsScan.delay}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void assignEvents() {
        log.debug("Получение новых мероприятий");
        List<Long> newEventsIdList = assignedEventsServiceImpl.getIdNotAssignedEvents();
        newEventsIdList.forEach(System.out::println);

        if (!newEventsIdList.isEmpty()) {
            if (moderatorSessionsServiceImpl.getFreeModerator() != null) {

                newEventsIdList.forEach(eventId -> {
                    assignedEventsRepository.save(AssignedEvents.builder()
                            .eventId(eventId)
                            .moderatorId(moderatorSessionsServiceImpl.getFreeModerator())
                            .build());
                });
                log.debug("Новые мероприятия получены и распределены на модераторов");
            } else {
                log.debug("Нет активных модераторов");
            }
        } else {
            log.debug("Новых мероприятий нет");
        }
    }

//    @Scheduled(fixedDelay = 100000)
//    @Bean("ModeratorSession")
//    public void moderatorSessionCreator() {
//        moderatorSessionRepository.save(ModeratorSession.builder()
//                .moderatorId(1L)
//                .amountOfModeratorsEvents(3L)
//                .createdSessionTime(null)
//                .lastUpdateSessionTime(null)
//                .build());
//
//        moderatorSessionRepository.save(ModeratorSession.builder()
//                .moderatorId(2L)
//                .amountOfModeratorsEvents(1L)
//                .createdSessionTime(null)
//                .lastUpdateSessionTime(null)
//                .build());
//
//        moderatorSessionRepository.save(ModeratorSession.builder()
//                .moderatorId(3L)
//                .amountOfModeratorsEvents(1L)
//                .createdSessionTime(null)
//                .lastUpdateSessionTime(null)
//                .build());
//
//    }
}
