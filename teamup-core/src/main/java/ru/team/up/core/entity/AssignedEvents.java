package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ASSIGNED_EVENTS")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class AssignedEvents {
    /**
     * Уникальный идентификатор
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID модератора
     */

    @Column(name = "moderator_id", nullable = false)
    private Long moderatorId;

    /**
     * ID мероприятия
     */
    @Column(name = "event_id", nullable = false)
    private Long eventId;
}
