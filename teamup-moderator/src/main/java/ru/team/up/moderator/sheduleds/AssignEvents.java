package ru.team.up.moderator.sheduleds;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Event;
import ru.team.up.moderator.service.AssignedEventsService;
import ru.team.up.moderator.service.AssignedEventsServiceImpl;

import java.util.List;


@Component
@Slf4j
public class AssignEvents {
    private AssignedEventsService assignedEventsService;

    @Autowired
    public AssignEvents(AssignedEventsService assignedEventsService) {
        this.assignedEventsService = assignedEventsService;
    }

    /**
     *
     * @return Метод возвращает List IDs Ивентов которые находятся на проверке
     */
    @Scheduled(fixedDelayString = "${eventsScan.delay}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public List<Integer> assignEvents() {
        log.debug("Получаем List IDs");
        return assignedEventsService.getEventsIds(assignedEventsService.getEventsForChecking());
    }
}
