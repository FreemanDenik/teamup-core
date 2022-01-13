package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.repositories.AssignedEventsRepository;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AssignedEventsServiceImpl implements AssignedEventsService{

    AssignedEventsRepository assignedEventsRepository;

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
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


}
