package ru.team.up.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс для передачи данных события
 */
@Data
public class EventReviewDTO {

    /**
     * ФИО пользователя, оставившего отзыв
     */
    private String reviewerFullName;

    /**
     * Email пользователя, оставившего отзыв
     */
    private String reviewerEmail;

    /**
     * Отзыв
     */
    private String reviewMessage;

    /**
     * Название мероприятия, для которого оставлен отзыв
     */
    private String eventName;

    /**
     * Время мероприятия, для которого оставлен отзыв
     */
    private LocalDateTime timeEvent;

    /**
     * Место мероприятия, для которого оставлен отзыв
     */
    private String placeEvent;

    /**
     * Время составления отзыва
     */

    private LocalDateTime reviewTime;

    /**
     * Оценка мероприятия
     */
    private Integer eventGrade;
}
