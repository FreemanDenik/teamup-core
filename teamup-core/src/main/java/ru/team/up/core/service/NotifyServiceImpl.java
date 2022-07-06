package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import ru.team.up.dto.NotifyDto;

/**
 * Author Nail Faizullin, Dmitry Koryanov
 * сервис-класс для отправки уведомлений
 */
@Slf4j
@Service
public class NotifyServiceImpl implements NotifyService{

    @Autowired
    WebClient webClient;

    @Override
    public void notify(NotifyDto notifyDto) {
        Flux<NotifyDto> notifyDtoFlux = Flux.just(notifyDto);
        log.debug("Отправляем POST запрос на помещение уведомления {} в очередь на отправку", notifyDto);
        callNotifyController(notifyDtoFlux);
    }

    @Override
    public void notify(Iterable<NotifyDto> notifyDtoIterable) {
        Flux<NotifyDto> notifyDtoFlux = Flux.fromIterable(notifyDtoIterable);
        log.debug("Отправляем POST запрос на помещение уведомлений в очередь на отправку");
        callNotifyController(notifyDtoFlux);
    }

    @Override
    public void callNotifyController(Flux<NotifyDto> notifyDtoFlux){

        webClient
                .post()
                .uri("/notify")
                .body(notifyDtoFlux, NotifyDto.class)
                .exchangeToFlux(response -> {
                    if (response.statusCode().equals(HttpStatus.CREATED)) {
                        return response.bodyToFlux(NotifyDto.class);
                    }
                    else {
                        log.debug("При отправке POST запроса на помещение уведомлений в очередь на отправку " +
                                        "возникла ошибка. Код ответа {}", response.statusCode());
                        return Flux.empty();
                    }
                })
                .subscribe(resultNotifyDto -> {log.debug("Уведомление {} успешно помещено в очередь на отправку",
                        resultNotifyDto);});
    }
}