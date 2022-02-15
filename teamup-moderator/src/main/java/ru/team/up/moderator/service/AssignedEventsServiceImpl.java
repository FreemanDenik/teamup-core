package ru.team.up.moderator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.repositories.AssignedEventsRepository;

import javax.management.Query;
import java.util.List;
import java.util.Random;

@Service
public class AssignedEventsServiceImpl implements AssignedEventsService {

    private AssignedEventsRepository assignedEventsRepository;

    @Autowired
    public AssignedEventsServiceImpl(AssignedEventsRepository assignedEventsRepository) {
        this.assignedEventsRepository = assignedEventsRepository;
    }

    public AssignedEvents getAssignedEvent(Long id) {
        return assignedEventsRepository.getOne(id);
    }

    public AssignedEvents saveAssignedEvent(AssignedEvents assignedEvents) {
        AssignedEvents save = assignedEventsRepository.saveAndFlush(assignedEvents);
        return save;
    }

    public void removeAssignedEvent(Long id) {
        try {
            assignedEventsRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getOtherEvents() {
        log.debug("Получаем из БД мероприятия находящиеся на проверке");
        Query query = session.createQuery("SELECT * FROM event AS E WHERE E.status_id = 2");
        List<Event> resultList = query.list();
        log.debug("Получаем ID мероприятий находящихся на проверке");
        Query query2 = session.createQuery("SELECT eventId FROM event WHERE eventId = :eventList");
        query2.setParameter("eventList", resultList);
        List<Integer> resultList2 = query.list();
        return resultList2;
    }
}
