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
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.EventReviewRepository;
import ru.team.up.core.repositories.UserMessageRepository;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDateTime;


/**
 * @author Stanislav Ivashchenko
 * <p>
 * Класс сервиса для управления отзывами ru.team.up.core.entity.EventReview
 */

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventReviewServiceImpl implements EventReviewService {
    private EventReviewRepository eventReviewRepository;
    private UserMessageRepository userMessageRepository;
    private SendMessageService sendMessageService;


    /**
     * Метод сохраняет отзыв для мероприятия
     * @param eventReview
     * @return
     */
    @Override
    @Transactional
    public EventReview saveEventReview(EventReview eventReview) {

        log.debug("Получаем мероприятие для которого оставили отзыв");
        Event event = eventReview.getReviewForEvent();

        log.debug("Получаем пользователя создавшего отзыв");
        User reviewer = eventReview.getReviewer();

        log.debug("Создаем и сохраняем сообщение");
        UserMessage message = UserMessage.builder()
                .messageOwner(reviewer)
                .message("Пользователь " + reviewer.getName()
                        + " написал отзыв о мероприятии " + event.getEventName()
                        + " и поставил оценку " + eventReview.getEventGrade())
                .status("new")
                .messageCreationTime(LocalDateTime.now()).build();
        userMessageRepository.save(message);

        log.debug("Отправка сообщения создателю мероприятия");
        sendMessageService.sendMessage(event.getAuthorId(), message);

        EventReview eventReviewSave = eventReviewRepository.save(eventReview);
        log.debug("Сохранили отзыв для мероприятия в БД {}", eventReviewSave);

        return eventReviewSave;
    }
}
