package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Сущность Мероприятий
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Event {
    /**
     * Первичный ключ
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название мероприятия
     */
    @Column(name = "EVENT_NAME", nullable = false)
    private String eventName;

    /**
     * Описание мероприятий
     */
    @Column(name = "DESCRIPTION_EVENT", nullable = false)
    private String descriptionEvent;

    /**
     * Место проведения мероприятия
     */
    @Column(name = "PLACE_EVENT", nullable = false)
    private String placeEvent;

    /**
     * Время проведения мероприятия
     */
    @Column(name = "TIME_EVENT", nullable = false)
    private LocalDateTime timeEvent;

    /**
     * Время обновления мероприятия
     */
    @Column(name = "EVENT_UPDATE_DATE")
    private LocalDate eventUpdateDate;

    /**
     * Участники мероприятия
     */
    @OneToMany(cascade = CascadeType.MERGE, fetch =FetchType.LAZY)
    @Column(name = "PARTICIPANTS_EVENT")
    private List<User> participantsEvent;

    /**
     * Тип мероприятия
     */
    @OneToOne(optional=false, cascade=CascadeType.MERGE)
    @JoinColumn(name = "EVENT_TYPE_ID")
    private EventType eventType;

    /**
     * Создатель мероприятия
     */
    @OneToOne(optional=false,cascade=CascadeType.MERGE)
    @JoinColumn(name = "USER_ID")
    private User authorId;

    /**
     * С какими интересами связанно мероприятие
     */
    @ManyToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinTable(name="INTERESTS_EVENT",
            joinColumns=@JoinColumn(name="EVENT_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "INTERESTS_ID"))
    private Set<Interests> eventInterests;

    /**
     * Статус мероприятия (модерация, доступно и т.д.)
     */
    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name = "STATUS_ID")
    private Status status;
}
