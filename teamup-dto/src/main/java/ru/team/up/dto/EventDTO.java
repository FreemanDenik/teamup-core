package ru.team.up.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Класс для передачи данных события
 */

@Data
public class EventDTO {
    /**
     * Название мероприятия
     */
    private String eventName;

    /**
     * Описание мероприятий
     */
    private String descriptionEvent;

    /**
     * Место проведения мероприятия
     */
    private String placeEvent;

    /**
     * Время проведения мероприятия
     */
    private LocalDateTime timeEvent;

    /**
     * Время обновления мероприятия
     */
    private LocalDate eventUpdateDate;

    /**
     * Приватность мероприятия
     */
    private Boolean eventPrivacy;

    /**
     * Участники мероприятия email:ФИО
     */
    private Map<String, String> participantsEventEmailFullName;

    /**
     * Тип мероприятия
     */
    private String eventType;

    /**
     * ФИО создателя мероприятия
     */
    private String authorFullName;

    /**
     * Email создателя мероприятия
     */
    private String authorEmail;


    /**
     * С какими интересами связанно мероприятие
     */
    private Set<String> eventInterests;

    /**
     * Статус мероприятия (модерация, доступно и т.д.)
     */
    private String status;
}
