package ru.team.up.moderator.sheduleds;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.repositories.AssignedEventsRepository;
import ru.team.up.core.repositories.ModeratorSessionRepository;
import ru.team.up.core.service.AssignedEventsServiceImpl;
import ru.team.up.moderator.service.ModeratorSessionsServiceImpl;

import java.time.LocalDateTime;
import java.util.List;


@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeleteModeratorsSessions {

    private ModeratorSessionsServiceImpl moderatorSessionsServiceImpl;
    private ModeratorSessionRepository moderatorSessionRepository;
    private AssignedEventsRepository assignedEventsRepository;
    private AssignedEventsServiceImpl assignedEventsServiceImpl;

    /**
     * Метод удаляет сессию модератора по активности (удаляет не активных по полю время прогрева)
     */
//    @Scheduled(fixedDelayString = "${moderatorActivity.delay}")
    @Scheduled(fixedRate = 10000)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void removeModeratorSession() {

        log.debug("Получение листа ID неактивных модераторов");
        List<Long> listInactiveModeratorsId = moderatorSessionsServiceImpl
                .getInactiveModerators(LocalDateTime.now().minusMinutes(1L));

        if (!listInactiveModeratorsId.isEmpty()) {
            listInactiveModeratorsId
                    .forEach(delete -> {
                        /*
                          Сброс мероприятий, назначенных на удаляемого модератора
                         */
                        assignedEventsServiceImpl.getIdAssignedEventsByModeratorId(delete)
                                .forEach(deleteEvents -> {
                                    assignedEventsRepository.deleteById(deleteEvents);
                                    log.debug("Мероприятие с ID " + deleteEvents + " было удалено");
                                });
                        /*
                         * Удаление неактивного модератора
                         */
                        moderatorSessionRepository.deleteById(delete);
                        log.debug("Модератор с ID " + delete + " был удален");
                    });
        } else {
            log.debug("Неактивных модераторов нет");
        }
    }
}
