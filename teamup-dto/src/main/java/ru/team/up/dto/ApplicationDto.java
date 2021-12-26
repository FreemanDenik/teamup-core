package ru.team.up.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Объект для передачи данных заявки на мероприятие
 */

@Data
@Builder
public class ApplicationDto {
    /**
     * Уникальный идентификатор
     */

    private Long id;


    /**
     * Пользователь
     */
    private UserDto user;

    /**
     * Мероприятие
     */
    private EventDto event;
}
