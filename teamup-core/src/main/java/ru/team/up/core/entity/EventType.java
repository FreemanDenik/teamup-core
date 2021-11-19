package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * Таблица типов мероприятия
 */
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT_TYPE")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class EventType {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Тип мероприятия: игра, встреча и т.д.
     */
    @Column(name = "TYPE", nullable = false)
    private String type;
}