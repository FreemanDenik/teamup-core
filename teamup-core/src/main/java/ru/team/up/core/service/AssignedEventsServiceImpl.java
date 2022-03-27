package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.AssignedEvents;
import ru.team.up.core.repositories.AssignedEventsRepository;

import java.util.List;

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

    @Transactional
    @Override
    public List<Long> getIdNotAssignedEvents() {
        return assignedEventsRepository.getIdNotAssignedEvents();
    }
}
