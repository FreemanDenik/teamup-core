package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.Event;
import ru.team.up.core.repositories.AssignedEventsRepository;

import java.util.List;

@Service
@Slf4j
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignedEventsServiceImpl implements AssignedEventsService {

    private final AssignedEventsRepository assignedEventsRepository;
    private final ModeratorsSessionsServiceImpl moderatorsSessionsServiceImpl;

    @Override
    public void removeAssignedEvent(Long id) {
        try {
            assignedEventsRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод сохраняет, распределенное на модератора, мероприятие
     * и инкрементирует счетчик мероприятий у модератора
     */
    @Transactional
    @Override
    public AssignedEvents saveAssignedEvent(AssignedEvents assignedEvents) {
        AssignedEvents save = assignedEventsRepository.saveAndFlush(assignedEvents);
        moderatorsSessionsServiceImpl.incrementModeratorEventCounter(save.getModeratorId());
        return save;
    }

    @Transactional
    @Override
    public List<Long> getIdNotAssignedEvents() {
        return assignedEventsRepository.getIdNotAssignedEvents();
    }

    @Transactional
    @Override
    public List<Long> getIdAssignedEventsByModeratorId(Long id) {
        return assignedEventsRepository.getIdAssignedEventsByModeratorId(id);
    }

    /**
     * @param id ID модератора
     * @return Список назначенных на него событий (AssignedEvents).
     * Если пользователь с переданным ID не найден в базе, генерирует исключение со статусом HttpStatus.NOT_FOUND
     */
    @Override
    //@Transactional(readOnly = true)
    public List<Event> getAllEventsByModeratorId(Long id) {

        log.debug("Получаем список назначенных событий на модератора {} в методе " +
                "AssignedEventsService.getAllEventsByModeratorId", id);

        List<Event> eventList = assignedEventsRepository.getAllEventsByModeratorId(id);

        eventList.forEach(e -> log.debug("Следующее назначенное на модератора {} событие {}", id, e.toString()));

        return eventList;
    }
}
