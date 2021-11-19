package ru.team.up.core.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "PLACE", nullable = false)
    private String place;

    @Column(name = "TIME_START", nullable = false)
    private String time;

    @Column(name = "PARTICIPANTS")
    private String participants;

    @OneToOne(optional=false, cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinColumn(name = "EVENT_TYPE_ID")
    private EventType eventType;

    @OneToOne(optional=false,cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User authorId;

    @OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY
            , mappedBy = "event")
    @Column(name = "INTERESTS_EVENT", nullable = false)
    private Set<Interests> eventInterests;

   @OneToOne(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
   @JoinColumn(name = "STATUS_ID")
    private Status status;
}
