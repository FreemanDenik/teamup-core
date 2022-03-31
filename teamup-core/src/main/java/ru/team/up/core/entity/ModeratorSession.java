package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@Table(name = "MODERATOR_SESSION")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@NoArgsConstructor
@AllArgsConstructor
public class ModeratorSession {
    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID модератора
     */
    @Column(name = "MODERATOR_ID")
    private long moderatorId;

    /**
     * Время создания сессии
     */
    @Column(name = "CREATED_SESSION_TIME")
    public LocalDateTime createdSessionTime;

    /**
     * Время прогрева сессии
     */
    @Column(name = "LAST_UPDATED_SESSION_TIME")
    public LocalDateTime lastUpdateSessionTime;

    /**
     * Количество обрабатываемых мероприятий у модератора
     */
    @Column(name = "AMOUNT_OF_MODERATORS_EVENTS")
    @NotNull
    private Long amountOfModeratorsEvents;

}
