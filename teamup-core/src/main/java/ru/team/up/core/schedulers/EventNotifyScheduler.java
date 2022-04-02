package ru.team.up.core.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;
import ru.team.up.core.service.EventService;
import ru.team.up.core.service.NotifyService;
import ru.team.up.dto.NotifyDto;
import ru.team.up.dto.NotifyStatusDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Naul Faizullin, Dmitry Koryanov
 * <p>
 * Класс планировщика (сервиса) для отправки уведомлений пользователям о предстоящих событиях
 */

@Slf4j
@Service
@PropertySource("classpath:notification.properties")
@ConditionalOnProperty(name = "notifications.enabled", matchIfMissing = false)
public class EventNotifyScheduler {

    @Autowired
    EventService eventService;

    @Autowired
    NotifyService notifyService;

    @Value("${notifications.datetime.pattern}")
    String dateTimeFormatterPattern;

    @Scheduled(fixedRateString = "${notifications.events.upcoming.frequency}")
    public void eventNotify(){

        log.debug("Сервис по рассылке уведомлений о предстоящих событиях запущен.");

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusHours(2);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatterPattern);

        log.debug("Получаем список событий с пользователями");

        Map<Event, List<User>> eventUsersMap = eventService.getEventsUsers(startDate, endDate);

        log.debug("Создаём список уведомлений");

        eventUsersMap
                .entrySet()
                .forEach(entry -> {
                    notifyService.notify(
                            entry
                                    .getValue()
                                    .stream()
                                    .map(u -> {
                                        return NotifyDto.builder()
                                                .email(u.getEmail())
                                                .text("Событие "+entry.getKey().getEventName()
                                                        + " состоится уже совсем скоро в "
                                                        + entry.getKey().getTimeEvent().format(dateTimeFormatter)
                                                        + " по адресу " + entry.getKey().getPlaceEvent())
                                                .subject("Напоминание о предстоящем событии \""
                                                        + entry.getKey().getEventName()+"\"")
                                                .creationTime(LocalDateTime.now())
                                                .status(NotifyStatusDto.NOT_SENT)
                                                .build();
                                    })
                                    .collect(Collectors.toList())
                            );
                });

        log.debug("Работа сервиса по рассылке уведомлений закончена");
    }
}
