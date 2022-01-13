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
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "moderator_id")
    private ModeratorsSessions moderatorsSessions;

    @OneToOne
    @MapsId
    @JoinColumn(name = "event_id")
    private Event event;
}
