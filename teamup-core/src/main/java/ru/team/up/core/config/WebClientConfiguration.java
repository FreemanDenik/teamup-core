package ru.team.up.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
@PropertySource("classpath:notification.properties")
public class WebClientConfiguration {

    @Value("${notification.uri.host}")
    private String notificationUriHost;

    @Bean
    public WebClient webClient(){
        log.debug("Создаём WebClient для отправки запросов по адресу {}",notificationUriHost);
        return WebClient.create(notificationUriHost);
    }

}
