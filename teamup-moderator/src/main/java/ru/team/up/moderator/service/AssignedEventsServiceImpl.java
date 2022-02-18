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

import javax.management.Query;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignedEventsServiceImpl implements AssignedEventsService {

    private final AssignedEventsRepository assignedEventsRepository;

    @Transactional(readOnly = true)
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

}
