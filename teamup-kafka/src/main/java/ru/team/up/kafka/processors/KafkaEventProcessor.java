package ru.team.up.kafka.processors;

import ru.team.up.dto.KafkaEventDto;

/**
 * Интерфейс для процессоров обработки событий KafkaEventDto
 */

public interface KafkaEventProcessor {

    /**
     * Метод обработки события
     */
    void perform(KafkaEventDto kafkaEventDto);
}
