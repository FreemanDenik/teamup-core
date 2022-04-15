package ru.team.up.kafka.processors;

import ru.team.up.dto.KafkaEventTypeDto;

/**
 * Интерфейс для процессоров обработки событий KafkaEventDto
 */

public interface KafkaEventProcessor {

    /**
     * Метод обработки события
     */
    void perform(KafkaEventTypeDto supportedType);
}
