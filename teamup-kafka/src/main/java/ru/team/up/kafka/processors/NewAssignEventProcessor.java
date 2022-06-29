package ru.team.up.kafka.processors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.team.up.dto.KafkaEventDto;
import ru.team.up.dto.KafkaEventTypeDto;
import ru.team.up.websocket.endpoint.ModeratorAssignEndpoint;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Процессор для обработки события "Назначение нового мероприятия на модератора"
 */

@Slf4j
@Component
public class NewAssignEventProcessor implements KafkaEventProcessor {

    @Value("${websocket.moderator.connect.uri}")
    private String moderatorConnectUri;

    /**
     * Метод обработки события "Назначение нового мероприятия на модератора"
     */
    @Override
    public void perform(KafkaEventDto kafkaEventDto) {
        log.debug("Старт обработки события назначения модератора на проверку ивента");
        ObjectMapper mapper = new ObjectMapper();
        String kafkaEventString = null;
        try {
            log.debug("Перевод KafkaEventDto в методе NewAssignEventProcessor.perform в строку");
            kafkaEventString = mapper.writeValueAsString(kafkaEventDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.debug("Создание эндпоинта и подключение к вебсокету...");
        try {
            URI uri = new URI(moderatorConnectUri);
            ModeratorAssignEndpoint endpoint = new ModeratorAssignEndpoint(uri);

            log.debug("Отправка kafkaEventDto на вебсокет...");
            endpoint.sendMessage(kafkaEventString);
        } catch (URISyntaxException e) {
            log.error("Ошибка в URI в методе perform NewAssignEventProcessor");
            e.printStackTrace();
        } catch (DeploymentException e) {
            log.error("DeploymentException: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.error("Ошибка при подключении к websocket в методе perform NewAssignEventProcessor");
            log.debug(e.getMessage());
            e.printStackTrace();
        }
    }
}
