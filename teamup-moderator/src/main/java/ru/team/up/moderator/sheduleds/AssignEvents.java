package ru.team.up.moderator.sheduleds;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.repositories.ModeratorRepository;
import ru.team.up.moderator.service.AssignedEventsService;


@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignEvents {
    private AssignedEventsService assignedEventsService;
    private final ModeratorRepository moderatorRepository;


    /**
     * Метод возвращает List IDs Ивентов которые находятся на проверке
     */
    //TODO Для тестов (далее будет логика распредления эвентов)
    @Scheduled(fixedDelayString = "${eventsScan.delay}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void assignEvents() {
        log.debug("Получаем List IDs");
        //Метод получаем лист мероприятий, у которых status_id = 2 (статус на проверке)
//        assignedEventsService.getEventsForChecking().
//                forEach(e -> {e.getId().equ
//                });
        for (Event e : assignedEventsService.getEventsForChecking()) {
            for (AssignedEvents a : assignedEventsService.getAssignedEvents()) {
                if (e.getId().equals(a.getEventId())) {
                    break;
                }
                assignedEventsService.saveAssignedEvent(new AssignedEvents(1L, 2L, e.getId()));
                //добавить мероприятие в таблицу AssignEvents и назначить модератора
            }

        }
        //Метод получает лист айдишников мероприятий, которые нуждаются в проверке
        //assignedEventsService.getEventsIds(assignedEventsService.getEventsForChecking()); //???
        //Метод получает все мероприятия, которые находятся на проверке по ID модератора
        assignedEventsService.getAllEventsByModeratorId();
        log.debug("Удалось получить следюущие мероприятия: {}", "");
        moderatorRepository.findAllByRole()
    }

}
//Каждые 5 минут пробегается по всем мероприятиям в статусе (на проверке) записывает их в список
//        и назначает на модераторов, перед назначением, смотрит, есть ли данное мероприятие в таблице AssignEvent,
//        если есть, пропускает, если нет ищет модератора



