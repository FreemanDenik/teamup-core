package ru.team.up.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Объект передачи данных интересов
 */

public class InterestsDTO {
    /**
     * Название
     */
      private String title;

    /**
     * Краткое описание
     */
    private String shortDescription;

    /**
     * Обладатели интереса мероприятия email:ФИО
     */
    private Map<String, String> usersEmailFullName;

    /**
     * Мероприятия, для которого проявляется интерес
     * Параметр: Значение
     */
    private Set<Map<String,String>>  event;

}
