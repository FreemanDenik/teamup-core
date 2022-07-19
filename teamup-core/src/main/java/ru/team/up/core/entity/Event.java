package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id") // аннотация не позволяет уйти в бесконечный цикл при сериализации
public class Event {
    /**
     * Первичный ключ
     */
    @EqualsAndHashCode.Include
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
     * Изображения мероприятия
     */
    @OneToOne(fetch = FetchType.EAGER)
    private Image image;

    /**
     * Город проведения мероприятия
     */
    @Column(name = "CITY_EVENT", nullable = false)
    private String city;

    /**
     * Время проведения мероприятия
     */
    @Column(name = "TIME_EVENT", nullable = false)
    private LocalDateTime timeEvent;

    /**
     * Время окончания мероприятия
     */
    @Column(name = "TIME_END_EVENT", nullable = false)
    private LocalDateTime timeEndEvent;

    /**
     * Время обновления мероприятия
     */
    @Column(name = "EVENT_UPDATE_DATE")
    private LocalDate eventUpdateDate;

    /**
     * Приватность мероприятия
     */
    @Column(name = "EVENT_PRIVACY")
    private Boolean eventPrivacy;

    /**
     * Число участников мероприятия
     */
    @Max(200)
    @Value("1")
    @Column(name = "EVENT_NUMBER_OF_PARTICIPANT", nullable = false)
    private Byte eventNumberOfParticipant;

    /**
     * Количество просмотров мероприятия
     */
    @Value("1")
    @Column(name = "COUNT_VIEW_EVENT", nullable = false)
    private Integer countViewEvent;

    /**
     * Участники мероприятия
     */
    @ManyToMany(mappedBy = "userEvent",
            cascade = CascadeType.MERGE, fetch =FetchType.LAZY)
    private Set<User> participantsEvent;

    /**
     * Тип мероприятия
     */
    @ManyToOne(optional=false, cascade=CascadeType.MERGE, fetch =FetchType.LAZY)
    @JoinColumn(name = "EVENT_TYPE_ID")
    private EventType eventType;

    /**
     * Создатель мероприятия
     */
    @ManyToOne(optional=false,cascade=CascadeType.MERGE, fetch =FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User authorId;

    /**
     * С какими интересами связано мероприятие
     */
    @ManyToMany(cascade=CascadeType.MERGE, fetch =FetchType.LAZY)
    @JoinTable(name="INTERESTS_EVENT",
            joinColumns=@JoinColumn(name="EVENT_ID", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "INTERESTS_ID"))
    @Column(name = "INTERESTS_EVENT")
    private Set<Interests> eventInterests;

    /**
     * Статус мероприятия (модерация, доступно и т.д.)
     */
    @ManyToOne(cascade=CascadeType.MERGE, fetch =FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID")
    private Status status;

    /**
     * Добавляет нового участника мероприятия
     * @param user
     */
    public void addParticipant(User user) {
        participantsEvent.add(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Event event = (Event) o;
        return id != null && Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
