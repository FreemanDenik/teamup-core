package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.entity.User;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.StatusRepository;
import ru.team.up.core.repositories.UserRepository;
import ru.team.up.dto.NotifyDto;
import ru.team.up.dto.NotifyStatusDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private NotifyService notifyService;

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
        if (haveNoRightsToSave(SecurityContextHolder.getContext().getAuthentication(), userCreatedEventDB)) {
            throw new SecurityException("Попытка сохранения события как другой пользователь");
        }

        log.debug("Формируем список подписчиков пользователя");
        Set<User> userSubscribers = userCreatedEventDB.getSubscribers();

        if (!userSubscribers.isEmpty()) {
            log.debug("Создание уведомления {} подписчикам", userSubscribers.size());

            String subject = "Новое мероприятие " + event.getEventName();
            String text = "Пользователь " + userCreatedEventDB.getUsername()
                    + " создал мероприятие " + event.getEventName()
                    + " с приватностью" + event.getEventPrivacy();

            notifyService.notify(userSubscribers.stream().map(u -> {
                return NotifyDto.builder()
                        .subject(subject)
                        .text(text)
                        .email(u.getEmail())
                        .status(NotifyStatusDto.NOT_SENT)
                        .creationTime(LocalDateTime.now())
                        .build();
            }).collect(Collectors.toList()));
        } else {
            log.debug("У пользователя нет подписчиков");
        }

        event.setEventUpdateDate(LocalDate.now());
        event.setAuthorId(userCreatedEventDB);
        event.setStatus(statusRepository.getOne(2L));

        log.debug("Старт метода сохранения мероприятия");
        Event save = eventRepository.save(event);
        log.debug("Успешно сохранили мероприятие с ID {} в БД ", save.getId());
        return save;
    }

    /**
     * Метод обновления ивента
     * @param event обновляемый ивент
     * @return обновленный ивент
     */
    @Override
    @Transactional
    public Event updateEvent(Event event) {
        log.debug("Старт метода сохранения ивента");

        Event oldEvent = eventRepository.getOne(event.getId());
        User userCreatedEventDB = (User) userRepository.findById(event.getAuthorId().getId()).get();

        if (haveNoRightsToSave(SecurityContextHolder.getContext().getAuthentication(), userCreatedEventDB)) {
            throw new SecurityException("Попытка изменения чужого события");
        }

        log.debug("Получили из БД пользователя с ID {}, отредактировавшего мероприятие {}",
                userCreatedEventDB.getId(), event.getEventName());

        event.setEventUpdateDate(LocalDate.now());
        event.setAuthorId(userCreatedEventDB);
        event.setCountViewEvent(oldEvent.getCountViewEvent());
        event.setStatus(statusRepository.getOne(2L));

        log.debug("Старт метода обновления мероприятия {}", event);
        Event update = eventRepository.save(event);
        log.debug("Успешно сохранили мероприятие с ID {} в БД ", update.getId());
        return update;
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

        log.debug("Отправка уведомления создателю c ID: {} для мероприятия с ID: {}", event.getAuthorId(), eventId);

        String message = "Пользователь " + user.getUsername()
                + " стал участником мероприятия " + event.getEventName();

        notifyService.notify(NotifyDto.builder()
                .email(event.getAuthorId().getEmail())
                .subject(message)
                .text(message)
                .status(NotifyStatusDto.NOT_SENT)
                        .creationTime(LocalDateTime.now())
                .build());

        event.addParticipant(user);
        log.debug("Добавили нового участника с ID {}", user.getId());

        Event save = eventRepository.save(event);
        log.debug("Сохранили мероприятие с ID {} в БД ", save.getId());
    }

    @Override
    @Transactional
    public void eventApprovedByModerator(Long eventId) {

        log.debug("Получаем мероприятие по ID");
        Event event = getOneEvent(eventId);

        log.debug("Меняем статус мероприятия на одобренный");
        event.setStatus((statusRepository.getOne(1L)));

        log.debug("Создаём уведомление автору мероприятия");

        String message = "Мероприятие " + event.getEventName() + " прошло проверку и одобрено модератором.";
        notifyService.notify(NotifyDto.builder()
                .email(event.getAuthorId().getEmail())
                .subject(message)
                .text(message)
                .status(NotifyStatusDto.NOT_SENT)
                .creationTime(LocalDateTime.now())
                .build());

        eventRepository.save(event);
        log.debug("Сохраняем мероприятие в БД {}", event.getEventName());
    }

    @Override
    @Transactional
    public void eventClosedByModerator(Long eventId) {

        log.debug("Получаем мероприятие {} по ID", eventId);
        Event event = getOneEvent(eventId);

        log.debug("Меняем статус мероприятия {} на закрытый модератором", event.getId());
        event.setStatus((statusRepository.getOne(4L)));

        log.debug("Отправляем сообщение создателю {} мероприятия {}", event.getAuthorId(), event.getId());

        String message = "Мероприятие " + event.getEventName() + " закрыто модератором.";

        notifyService.notify(NotifyDto.builder()
                .email(event.getAuthorId().getEmail())
                .subject(message)
                .text(message)
                .status(NotifyStatusDto.NOT_SENT)
                .creationTime(LocalDateTime.now())
                .build());

        eventRepository.save(event);
        log.debug("Сохраняем мероприятие {} в БД ", event.getId());
    }

    @Override
    @Transactional
    public void updateNumberOfViews(Long id) {

        log.debug("Обновляем количество участников мероприятия {} по ID", id);
        eventRepository.updateNumberOfViews(id);
        log.debug("Обновили количество участников мероприятия {} по ID", id);
    }


    @Override
    @Transactional
    public void incrementCountViewEvent(Long id) {

        log.debug("Увеличиваем количество просмотров мероприятия на 1 {} по ID", id);
        eventRepository.incrementCountViewEvent(id);
        log.debug("Увеличили количество просмотров мероприятия на 1 {} по ID", id);
    }
    /**
     * @param subscriberId Id пользователя
     * @return Поиск мероприятий на которые подписан пользователь
     */
    @Override
    @Transactional(readOnly = true)
    public List<Event> getAllEventsBySubscriberId(Long subscriberId) {
        log.debug("Старт метода Поиск мероприятий на которые подписан пользователь");
        return eventRepository.getAllEventsBySubscriberId(subscriberId);
    }

    /**
     * @author Nail Faizullin, Dmitry Koryanov
     * @param startDateTime Время события мероприятия от
     * @param endDateTime Время события мероприятия до
     *           Метод получает map-у с предстоящими в указанном интервале событиями и пользователями, которые
     *                    участвуют в этих событиях
     */
    @Override
    public Map<Event, List<User>> getEventsUsers(LocalDateTime startDateTime, LocalDateTime endDateTime) {

        log.debug("Ищем мероприятия с участвующими в них пользователями. Время события мероприятия от: "+startDateTime);
        log.debug("Ищем мероприятия с участвующими в них пользователями. Время события мероприятия до: "+endDateTime);

        return eventRepository
                .findByTimeEventBetween(startDateTime, endDateTime)
                .stream()
                .collect(Collectors.toMap(e -> e, e -> {
                    return eventRepository
                            .getEventUserIds(e.getId())
                            .stream()
                            .map(userId -> userRepository.findUserById(userId))
                            .collect(Collectors.toList());
                }));
    }

    /**
     *
     * @param authentication параметр из SecurityContext текущей сессии
     * @param user пользователь, которому принадлежит мероприятие
     * @return true, если изменения производит админ, модератор, или юзер над своим мероприятием
     */
    private boolean haveNoRightsToSave(Authentication authentication, User user) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof Admin || principal instanceof Moderator) {
            return false;
        } else if (principal instanceof User) {
            User currentUser = (User) principal;
            return !currentUser.getId().equals(user.getId());
        }
        return true;
    }
}
