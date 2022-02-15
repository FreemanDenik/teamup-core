package ru.team.up.moderator.sheduleds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import ru.team.up.moderator.service.AssignedEventsService;
import ru.team.up.moderator.service.AssignedEventsServiceImpl;

import java.util.List;

@Component
public class AssignEvents {
    private AssignedEventsService assignedEventsService;

    @Autowired
    public AssignEvents(AssignedEventsService assignedEventsService) {
        this.assignedEventsService = assignedEventsService;
    }

    @Scheduled(fixedDelayString = "${eventsScan.delay}")
    @Transaсtional(isolation = Isolation.REPEATABLE_READ)
    public List<Integer> assignEvents() {
        return assignedEventsService.getOtherEvents();
    }
}
