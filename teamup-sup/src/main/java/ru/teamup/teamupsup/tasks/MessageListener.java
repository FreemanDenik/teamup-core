package ru.teamup.teamupsup.tasks;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.team.up.dto.SupParameterDto;

/**
 * @author STepan Glushchenko
 * Класс получает сообщения kafka по указанным топикам
 */

@Slf4j
@Component
public class MessageListener {
    /**
     * Получает сообщения из kafka
     *
     * @param parameter объект для изменения конфигурации системы
     */
    @KafkaListener(
            topics = "${kafka.topic.name}",
            groupId = "${kafka.group.id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void receiveMessage(SupParameterDto<?> parameter) {
        log.debug("Received message: " + parameter);
        //todo:
    }
}