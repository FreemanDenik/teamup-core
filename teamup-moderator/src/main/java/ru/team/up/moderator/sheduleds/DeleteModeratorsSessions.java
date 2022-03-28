package ru.team.up.moderator.sheduleds;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.service.AssignedEventsServiceImpl;
import ru.team.up.core.service.ModeratorsSessionsServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DeleteModeratorsSessions {

    private ModeratorsSessionsServiceImpl moderatorSessionsServiceImpl;
    private AssignedEventsServiceImpl assignedEventsServiceImpl;

    /**
     * Метод удаляет сессию модератора по активности (удаляет не активных по полю время прогрева)
     */
//    @Scheduled(fixedRate = 10000)
    @Scheduled(fixedDelayString = "${moderatorActivity.delay}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void removeModeratorSession() {

        log.debug("Получение листа ID неактивных модераторов");
        List<Long> listInactiveModeratorsId = moderatorSessionsServiceImpl
                .getInactiveModerators(LocalDateTime.now().minusMinutes(30L));

        if (!listInactiveModeratorsId.isEmpty()) {
            listInactiveModeratorsId
                    .forEach(delete -> {
                        /*
                          Сброс всех мероприятий, назначенных на неактивного модератора
                         */
                        assignedEventsServiceImpl.getIdAssignedEventsByModeratorId(delete)
                                .forEach(deleteEvents -> {
                                    assignedEventsServiceImpl.removeAssignedEvent(deleteEvents);
                                    log.debug("Мероприятие с ID " + deleteEvents + " было удалено");
                                });
                        /*
                         * Удаление сессии неактивного модератора
                         */
                        moderatorSessionsServiceImpl.removeModeratorSession(delete);
                        log.debug("Модератор с ID " + delete + " был удален");
                    });
        } else {
            log.debug("Неактивных модераторов нет");
        }
    }
}
