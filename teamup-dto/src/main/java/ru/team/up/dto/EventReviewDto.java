package ru.team.up.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс для передачи данных события
 */
@Data
@Builder
public class EventReviewDto {
    /**
     * Первичный ключ
     */
    private Long reviewId;

    /**
     * Пользователь оставивший отзыв
     */
    private UserDto reviewer;

    /**
     * Отзыв
     */
    private String reviewMessage;

    /**
     * Для какого мероприятия оставлен отзыв
     */
    private EventDto reviewForEvent;

    /**
     * Время составления отзыва
     */
    private LocalDateTime reviewTime;

    /**
     * Оценка мероприятия
     */
    private Integer eventGrade;
}
