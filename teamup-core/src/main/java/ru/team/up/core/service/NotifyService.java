package ru.team.up.core.service;

import reactor.core.publisher.Flux;
import ru.team.up.dto.NotifyDto;

/**
 * Author Nail Faizullin, Dmitry Koryanov
 * интерфейс для отправки уведомлений
 */
public interface NotifyService {

    void notify(NotifyDto notifyDto);

    void notify(Iterable<NotifyDto> notifyDtoList);

    void callNotifyController(Flux<NotifyDto> notifyDtoFlux);
}
