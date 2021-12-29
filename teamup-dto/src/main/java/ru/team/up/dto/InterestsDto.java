package ru.team.up.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

/**
 * Объект передачи данных интересов
 */
@Data
@Builder
public class InterestsDto {
    /**
     * Первичный ключ
     */
    private Long id;

    /**
     * Название
     */
      private String title;

    /**
     * Краткое описание
     */
    private String shortDescription;

    /**
     * Пользователи
     */
    private Set<UserDto> users;

    /**
     * Мероприятия, для которых проявляется интерес
     */
    private Set<EventDto> event;

}
