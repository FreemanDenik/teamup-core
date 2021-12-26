package ru.team.up.dto;

import lombok.Builder;
import lombok.Data;
/**
 * Объект передачи данных типов мероприятия
 */

@Data
@Builder
public class EventTypeDto {
    /**
     * Первичный ключ
     */
    private Long id;

    /**
     * Тип мероприятия: игра, встреча и т.д.
     */
    private String type;
}
