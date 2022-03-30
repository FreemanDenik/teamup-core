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
}
