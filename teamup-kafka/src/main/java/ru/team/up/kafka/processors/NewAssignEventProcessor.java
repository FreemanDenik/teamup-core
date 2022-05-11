package ru.team.up.kafka.processors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.team.up.dto.KafkaEventDto;
import ru.team.up.dto.KafkaEventTypeDto;

/**
 * Процессор для обработки события "Назначение нового мероприятия на модератора"
 */

@Slf4j
@Component
public class NewAssignEventProcessor implements KafkaEventProcessor{

    /**
     * Тип событий, которые обрабатывает процессор
     */
    private static final KafkaEventTypeDto supportType = KafkaEventTypeDto.NEW_ASSIGN_EVENT;

    /**
     * Метод обработки события "Назначение нового мероприятия на модератора"
     */
    @Override
    public void perform(KafkaEventDto kafkaEventDto) {
        //TODO Добавить в параметры необходимые данные из KafkaEventDto
        //TODO Добавить логику обработки события
    }
}
