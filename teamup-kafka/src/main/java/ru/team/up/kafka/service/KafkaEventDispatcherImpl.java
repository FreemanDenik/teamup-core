package ru.team.up.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.team.up.dto.KafkaEventDto;
import ru.team.up.dto.KafkaEventTypeDto;
import ru.team.up.kafka.exception.IncorrectKafkaEventTypeException;
import ru.team.up.kafka.processors.KafkaEventProcessor;
import ru.team.up.kafka.processors.NewAssignEventProcessor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Класс-диспетчер, который получает события их Kafka и распределяет их по процессорам
 */

@Slf4j
@Component
public class KafkaEventDispatcherImpl implements KafkaEventDispatcher{

    private KafkaTemplate kafkaTemplate;
    private Map<KafkaEventTypeDto, KafkaEventProcessor> kafkaEventProcessorMap;

    @Autowired
    public KafkaEventDispatcherImpl(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        kafkaEventProcessorMap = new ConcurrentHashMap<>();
        // добавление процессора для обработки события назначения модератору ивента на проверку
        kafkaEventProcessorMap.put(KafkaEventTypeDto.NEW_ASSIGN_EVENT, new NewAssignEventProcessor());
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(KafkaEventDto kafkaEvent) {
        log.debug("New kafka message: {}", kafkaEvent.toString());

        if (kafkaEventProcessorMap.containsKey(kafkaEvent.getKafkaEventTypeDto())) {
            kafkaEventProcessorMap.get(kafkaEvent.getKafkaEventTypeDto()).perform(kafkaEvent);
        } else {
            throw new IncorrectKafkaEventTypeException();
        }
    }
}
