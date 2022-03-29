package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.team.up.dto.NotificationDto;

/**
 * Author Nail Faizullin, Dmitry Koryanov
 * сервис-класс для отправки уведомлений
 */

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    WebClient webClient;

    @Override
    public void sendNotification(NotificationDto notificationDto) {
        Mono<NotificationDto> notificationDtoMono = Mono.just(notificationDto);

        log.debug("Отправляем POST запрос на помещение уведомления {} в очередь на отправку", notificationDto);

        webClient
                .post()
                .uri("/notification")
                .body(notificationDtoMono, NotificationDto.class)
                .exchangeToMono(response -> {
                    if (response.statusCode().equals(HttpStatus.CREATED)) {
                        return response.bodyToMono(NotificationDto.class);
                    }
                    else {
                        log.debug("При отправке POST запроса на помещение уведомления {} в очередь на отправку " +
                                        "возникла ошибка. Код ответа {}",
                                notificationDto, response.statusCode());
                        return Mono.empty();
                    }
                })
            .subscribe(resultNotificationDto -> {log.debug("Уведомление {} успешно помещено в очередь на отправку",
                    resultNotificationDto);});

    }
}
