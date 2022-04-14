package ru.team.up.kafka.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.team.up.dto.KafkaEventDto;
import ru.team.up.dto.KafkaEventTypeDto;
import ru.team.up.kafka.exception.IncorrectKafkaEventTypeException;
import ru.team.up.kafka.processors.KafkaEventProcessor;
import java.util.Map;

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
    }

    @Override
    @KafkaListener(topics = "${kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(KafkaEventDto kafkaEvent) {

        if (kafkaEventProcessorMap.containsKey(kafkaEvent.getKafkaEventTypeDto())) {
            kafkaEventProcessorMap.get(kafkaEvent.getKafkaEventTypeDto()).perform(kafkaEvent.getKafkaEventTypeDto());
        } else {
            throw new IncorrectKafkaEventTypeException();
        }
    }
}
