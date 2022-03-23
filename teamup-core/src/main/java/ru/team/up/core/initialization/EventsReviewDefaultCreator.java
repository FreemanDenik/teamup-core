package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import ru.team.up.core.entity.EventReview;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.EventReviewRepository;
import ru.team.up.core.repositories.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Component
@Transactional
@Profile("cdb")
public class EventsReviewDefaultCreator {

    private final EventReviewRepository eventReviewRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Autowired
    public EventsReviewDefaultCreator(EventReviewRepository eventReviewRepository, UserRepository userRepository,
                                      EventRepository eventRepository) {
        this.eventReviewRepository = eventReviewRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }


    @Bean("EventsReviewDefaultCreator")
    @DependsOn({"EventsDefaultCreator", "UsersDefaultCreator"})
    public void eventsReviewDefaultCreator() {

        eventReviewRepository.save(EventReview.builder()
                .reviewer(userRepository.findUserById(7L))
                .reviewMessage("Для многоих Хакатон это отличный старт, своего рода навигатор дальнейшего профессионального развития.")
                .reviewForEvent(eventRepository.getOne(1L))
                .reviewTime(LocalDateTime.of(2021,9,26,18,0))
                .eventGrade(10)
                .build());
    }
}