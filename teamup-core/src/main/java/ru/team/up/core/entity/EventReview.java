package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * Сущность для отзывов мероприятий
 *
 * @author Pavel Kondrashov
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "EVENT_REVIEW")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class EventReview {

    /**
     * Первичный ключ
     */
    @Id
    @Column(name = "REVIEW_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    /**
     * Пользователь оставивший отзыв
     */
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "USER_ID")
    private User reviewer;

    /**
     * Отзыв
     */
    @Column(name = "REVIEW_MESSAGE")
    private String reviewMessage;

    /**
     * Для какого мероприятия оставлен отзыв
     */
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "EVENT_ID")
    private Event reviewForEvent;

    /**
     * Время составления отзыва
     */
    @Column(name = "REVIEW_TIME")
    private LocalDateTime reviewTime;

    /**
     * Оценка мероприятия
     */
    @Min(1)
    @Max(10)
    @Column(name = "EVENT_GRADE")
    private Integer eventGrade;
}
