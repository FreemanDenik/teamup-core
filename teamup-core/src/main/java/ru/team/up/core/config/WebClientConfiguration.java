package ru.team.up.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;
import ru.team.up.sup.service.ParameterService;

@Slf4j
@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        String notificationUriHost = ParameterService.getNotificationUriHost.getValue();

        log.debug("Создаём WebClient для отправки запросов по адресу {}", notificationUriHost);
        return WebClient.create(notificationUriHost);
    }
}
