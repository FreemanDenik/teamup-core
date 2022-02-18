package ru.team.up.moderator.sheduleds;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.moderator.service.AssignedEventsService;


@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignEvents {
    private AssignedEventsService assignedEventsService;

    /**
     * Метод возвращает List IDs Ивентов которые находятся на проверке
     */
    //TODO Для тестов (далее будет логика распредления эвентов)
    @Scheduled(fixedDelayString = "${eventsScan.delay}")
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void assignEvents() {
        log.debug("Получаем List IDs");

        log.debug("Удалось получить следюущие мероприятия: {}", "");
    }
}
