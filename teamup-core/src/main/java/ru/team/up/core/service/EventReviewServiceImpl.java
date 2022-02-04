package ru.team.up.core.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.EventReview;
import ru.team.up.core.entity.User;
import ru.team.up.core.entity.UserMessage;
import ru.team.up.core.repositories.EventReviewRepository;
import ru.team.up.core.repositories.StatusRepository;
import ru.team.up.core.repositories.UserMessageRepository;

import java.time.LocalDateTime;


@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventReviewServiceImpl implements EventReviewService {
    private EventReviewRepository eventReviewRepository;
    private UserMessageRepository userMessageRepository;
    private SendMessageService sendMessageService;
    private StatusRepository statusRepository;


    @Override
    @Transactional
    public EventReview saveEventReview(EventReview eventReview) {
        log.debug("Старт метода сохранения отзыва для мероприятия");

        Event event = eventReview.getReviewForEvent();
        log.debug("Получили мероприятие с ID {} для которого оставили отзыв", event.getId());

        User reviewer = eventReview.getReviewer();
        log.debug("Получили пользователя с ID {} создавшего отзыв", reviewer.getId());

        log.debug("Создаем и сохраняем сообщение");
        UserMessage message = UserMessage.builder()
                .messageOwner(reviewer)
                .message("Пользователь " + reviewer.getUsername()
                        + " написал отзыв о мероприятии " + event.getEventName()
                        + " и поставил оценку " + eventReview.getEventGrade())
                .status(statusRepository.getOne(5L))
                .messageCreationTime(LocalDateTime.now()).build();
        userMessageRepository.save(message);

        sendMessageService.sendMessage(event.getAuthorId(), message);
        log.debug("Отправили сообщение создателю c ID {} мероприятия ", event.getAuthorId());

        EventReview eventReviewSave = eventReviewRepository.save(eventReview);
        log.debug("Сохранили отзыв для мероприятия с ID {} в БД ", eventReviewSave.getReviewForEvent().getId());

        return eventReviewSave;
    }
}
