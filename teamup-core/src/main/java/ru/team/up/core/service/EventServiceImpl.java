package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;
import ru.team.up.core.entity.UserMessage;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.StatusRepository;
import ru.team.up.core.repositories.UserMessageRepository;
import ru.team.up.core.repositories.UserRepository;
import ru.team.up.dto.NotificationDto;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Alexey Tkachenko
 * <p>
 * Класс сервиса для управления мероприятиями ru.team.up.core.entity.Event
 */

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
    private UserRepository userRepository;
    private StatusRepository statusRepository;
    private UserMessageRepository userMessageRepository;
    private NotificationService notificationService;

    /**
     * @return Возвращает коллекцию Event.
     * Если коллекция пуста, генерирует исключение со статусом HttpStatus.NO_CONTENT
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        log.debug("Старт метода получения всех мероприятий");

        List<Event> events = Optional.of(eventRepository.findAll())
                .orElseThrow(NoContentException::new);
        log.debug("Получили список из {} мероприятий из БД", events.size());

        return events;
    }

    /**
     *
     * @param authorId Id пользователя
     * @return Получение всех мероприятий пользователя
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllByAuthorId(Long authorId) {
        log.debug("Старт метода получения всех мероприятий пользователя");
        return eventRepository.findAllByAuthorId(authorId);
    }

    /**
     *
     * @param city город проведения мероприятий
     * @return Получение всех мероприятий в городе
     */
    @Override
    public List<Event> getAllEventsByCity(String city) {
        log.debug("Старт метода получения всех мероприятий в городе");
        return eventRepository.findAllByCity(city);
    }

    /**
     * @param id Уникальный ключ ID мероприятия
     * @return Находит в БД мероприятие по ID и возвращает его.
     * Если мероприятие с переданным ID не найдено в базе, генерирует исключение со статусом HttpStatus.NOT_FOUND
     */
    @Override
    @Transactional(readOnly = true)
    public Event getOneEvent(Long id) {
        log.debug("Старт метода получения мероприятия по ID {}", id);

        Event event = Optional.of(eventRepository.getOne(id))
                .orElseThrow(() -> new UserNotFoundIDException(id));

        log.debug("Получили мероприятие из БД с ID {}", event.getId());

        return event;
    }

    /**
     * @param event Объект класса ru.team.up.core.entity.Event
     * @return Возвращает сохраненный в БД объект event
     */
    @Override
    @Transactional
    public Event saveEvent(Event event) {
        log.debug("Старт метода сохранения мероприятия");

        User userCreatedEventDB = (User) userRepository.findById(event.getAuthorId().getId()).get();
        log.debug("Получили из БД пользователя с ID {}, создавшего мероприятие {}", userCreatedEventDB.getId(), event.getEventName());

        log.debug("Формируем список подписчиков пользователя");
        Set<User> userSubscribers = userCreatedEventDB.getSubscribers();


        log.debug("Создаем и сохраняем сообщение");
        UserMessage message = UserMessage.builder().messageOwner(userCreatedEventDB)
                .message("Пользователь " + userCreatedEventDB.getUsername()
                        + " создал мероприятие " + event.getEventName()
                        + " с приватностью" + event.getEventPrivacy())
                .status(statusRepository.getOne(5L))
                .messageCreationTime(LocalDateTime.now()).build();
        userMessageRepository.save(message);

        log.debug("Отправка сообщения {} подписчикам", userSubscribers.size());
        sendMessageService.sendMessage(userSubscribers, message);

        log.debug("Старт метода сохранения мероприятия");
        Event save = eventRepository.save(event);
        log.debug("Успешно сохранили мероприятие с ID {} в БД ", save.getId());

        return save;
    }

    /**
     * @param id Объект класса ru.team.up.core.entity.Event
     *           Метод удаляет мероприятие из БД
     */
    @Override
    @Transactional
    public void deleteEvent(Long id) {
        log.debug("Старт метода удаления мероприятия с ID {}", id);

        eventRepository.deleteById(id);
        log.debug("Удалили мероприятие c ID {} из БД ", id);
    }

    /**
     * Добавляет нового участника к мероприятию
     */
    @Override
    @Transactional
    public void addParticipantEvent(Long eventId, User user) {
        log.debug("Старт метода добавления участника в мероприятие");

        log.debug("Получаем мероприятие по ID: {}", eventId);
        Event event = getOneEvent(eventId);

        log.debug("Создаем и сохраняем сообщение");
        NotificationDto message = NotificationDto.builder()
                .text("Пользователь " + user.getUsername()
                        + " стал участником мероприятия " + event.getEventName())
                .subject("Новый участник!!!")
                .email(event.getAuthorId().getEmail())
                .status(NotificationDto.Status.NOT_SENT)
                .creationTime(LocalDateTime.now())
                .build();

        log.debug("Отправка сообщения создателю c ID: {} для мероприятия с ID: {}", event.getAuthorId(), eventId);
        notificationService.sendNotification(message);

        event.addParticipant(user);
        log.debug("Добавили нового участника с ID {}", user.getId());

        Event save = eventRepository.save(event);
        log.debug("Сохранили мероприятие с ID {} в БД ", save.getId());
    }
        //TODO удалить если код он в дальнейшем не используется
//        UserMessage message = UserMessage.builder()
//                .messageOwner(user)
//                .message("Пользователь " + user.getUsername()
//                        + " стал участником мероприятия " + event.getEventName())
//                .status(statusRepository.getOne(5L))
//                .messageCreationTime(LocalDateTime.now()).build();
//        userMessageRepository.save(message);

//        log.debug("Отправка сообщения создателю c ID: {} для мероприятия с ID: {}", event.getAuthorId(), eventId);
//        sendMessageService.sendMessage(event.getAuthorId(), message);

//        event.addParticipant(user);
//        log.debug("Добавили нового участника с ID {}", user.getId());
//
//        Event save = eventRepository.save(event);
//        log.debug("Сохранили мероприятие с ID {} в БД ", save.getId());
//    }

    @Override
    @Transactional
    public void eventApprovedByModerator(Long eventId){

        log.debug("Получаем мероприятие по ID");
        Event event = getOneEvent(eventId);

        log.debug("Меняем статус мероприятия на одобренный");
        event.setStatus((statusRepository.getOne(1L)));

        log.debug("Создаем и сохраняем сообщение");
        NotificationDto message = NotificationDto.builder()
                .text("Мероприятие " + event.getEventName() + " прошло проверку и одобрено модератором.")
                .subject("Мероприятие одобрено модератором!!!")
                .email(event.getAuthorId().getEmail())
                .status(NotificationDto.Status.NOT_SENT)
                .creationTime(LocalDateTime.now())
                .build();

        log.debug("Отправляем сообщение создателю мероприятия");
        notificationService.sendNotification(message);

        eventRepository.save(event);
        log.debug("Сохраняем мероприятие в БД {}", event.getEventName());

        //TODO удалить если код он в дальнейшем не используется
//        log.debug("Создаем и сохраняем сообщение");
//        UserMessage message = UserMessage.builder()
//                .messageOwner(event.getAuthorId())
//                .message("Мероприятие " + event.getEventName() + " прошло проверку и одобрено модератором.")
//                .status(statusRepository.getOne(5L))
//                .messageCreationTime(LocalDateTime.now()).build();
//        userMessageRepository.save(message);
//
//        log.debug("Отправляем сообщение создателю мероприятия");
//        sendMessageService.sendMessage(event.getAuthorId(), message);
//
//        eventRepository.save(event);
//        log.debug("Сохраняем мероприятие в БД {}", event.getEventName());
    }

    @Override
    @Transactional
    public void eventClosedByModerator(Long eventId){

        log.debug("Получаем мероприятие {} по ID", eventId);
        Event event = getOneEvent(eventId);

        log.debug("Меняем статус мероприятия {} на закрытый модератором", event.getId());
        event.setStatus((statusRepository.getOne(4L)));

        log.debug("Создаем и сохраняем сообщение");
        NotificationDto message = NotificationDto.builder()
                .text("Мероприятие " + event.getEventName() + " закрыто модератором.")
                .subject("event.getEventName() + закрыто модератором.!!!")
                .email(event.getAuthorId().getEmail())
                .status(NotificationDto.Status.NOT_SENT)
                .creationTime(LocalDateTime.now())
                .build();

        log.debug("Отправляем сообщение создателю {} мероприятия {}", event.getAuthorId(), event.getId());
        notificationService.sendNotification(message);

        eventRepository.save(event);
        log.debug("Сохраняем мероприятие {} в БД ", event.getId());

        //TODO удалить если код он в дальнейшем не используется
//        log.debug("Создаем и сохраняем сообщение");
//        UserMessage message = UserMessage.builder()
//                .messageOwner(event.getAuthorId())
//                .message("Мероприятие " + event.getEventName() + " закрыто модератором.")
//                .status(statusRepository.getOne(5L))
//                .messageCreationTime(LocalDateTime.now()).build();
//        userMessageRepository.save(message);
//
//        log.debug("Отправляем сообщение создателю {} мероприятия {}", event.getAuthorId(), event.getId());
//        sendMessageService.sendMessage(event.getAuthorId(), message);
//
//        eventRepository.save(event);
//        log.debug("Сохраняем мероприятие {} в БД ", event.getId());
    }

    @Override
    @Transactional
    public void updateNumberOfViews(Long id) {

        log.debug("Обновляем количество просмотров мероприятия {} по ID", id);
        eventRepository.updateNumberOfViews(id);
        log.debug("Обновили количество просмотров мероприятия {} по ID", id);
    }

    /**
     *
     * @param subscriberId Id пользователя
     * @return Поиск мероприятий на которые подписан пользователь
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllEventsBySubscriberId(Long subscriberId) {
        log.debug("Старт метода Поиск мероприятий на которые подписан пользователь");
        return eventRepository.getAllEventsBySubscriberId(subscriberId);
    }
}
