package ru.team.up.moderator.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.repositories.AssignedEventsRepository;
import ru.team.up.moderator.sheduleds.AssignEvents;

import javax.management.Query;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignedEventsServiceImpl implements AssignedEventsService {

    private final AssignedEventsRepository assignedEventsRepository;


    /**
     * Метод получаем лист мероприятий, у которых status_id = 2 (статус на проверке)
     *
     * @return лист мероприятий, нуждающихся в проверке
     */
    @Override
    public List<Event> getEventsForChecking() {
        return assignedEventsRepository.getEventsForChecking();
    }

    /**
     * Метод получает лист айдишников мероприятий, которые нуждаются в проверке
     *
     * @param eventList лист мероприятий, нуждающихся в проверке
     * @return лист айдишников мероприятий, нуждающихся в проверке
     */
    @Override
    public List<Integer> getEventsIds(List<Event> eventList) {
        return assignedEventsRepository.getEventsIds(eventList);
    }

    /**
     * Метод получает все мероприятия, которые находятся на проверке по ID модератора
     *
     * @param id модератора
     * @return лист проверяющихся мероприятий конкретного модератора
     */
    @Override
    public List<AssignedEvents> getAllEventsByModeratorId(Long id) {
        return assignedEventsRepository.getAllEventsByModeratorId(id);
    }

    /**
     * Метод меняет статус (STATUS_ID) мероприятия на 1 (доступно) перед тем, как открепить это мероприятие
     * от сессии модератора и перед тем, как удалить эту сессию модератора
     */
    @Override
    public void updateEventIdBeforeDeleting(Long statusId, Long eventId) {
        assignedEventsRepository.updateEventIdBeforeDeleting(statusId, eventId);
    }



    /**
     *
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public AssignedEvents getAssignedEvent(Long id) {
        return assignedEventsRepository.getOne(id);
    }

    /**
     *
     * @param
     * @return
     */
    @Transactional
    public AssignedEvents saveAssignedEvent(AssignedEvents assignedEvents) {
        AssignedEvents save = assignedEventsRepository.saveAndFlush(assignedEvents);
        return save;
    }

    /**
     *
     * @param id
     * @return
     */
    @Transactional
    public void removeAssignedEvent(Long id) {
        assignedEventsRepository.deleteById(id);
    }

    //////////////
    @Override
    public List<AssignedEvents> getAssignedEvents() {
        return assignedEventsRepository.getAssignedEvents();
    }
    /////////////
}
