package ru.team.up.kafka.service;

import ru.team.up.dto.KafkaEventDto;

/**
 * Интерфейс для диспетчера событий KafkaEventDto
 */

public interface KafkaEventDispatcher {
    void listen(KafkaEventDto kafkaEvent);
}
