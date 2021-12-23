package ru.team.up.dto;

import lombok.Data;
/**
 * Объект передачи данных типов мероприятия
 */

@Data
public class EventTypeDTO {
    /**
     * Тип мероприятия: игра, встреча и т.д.
     */
    private String type;
}
