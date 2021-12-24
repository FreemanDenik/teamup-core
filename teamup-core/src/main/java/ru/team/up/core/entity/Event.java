package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
     * Приватность мероприятия
     */
    @Column(name = "EVENT_PRIVACY")
    private Boolean eventPrivacy;

    /**
     * Участники мероприятия
     */
    @ManyToMany(mappedBy = "userEvent",
            cascade = CascadeType.MERGE, fetch =FetchType.LAZY)
    private Set<User> participantsEvent;

    /**
     * Тип мероприятия
     */
    @ManyToOne(optional=false, cascade=CascadeType.MERGE)
    @JoinColumn(name = "EVENT_TYPE_ID")
    private EventType eventType;

    /**
     * Создатель мероприятия
     */
    @ManyToOne(optional=false,cascade=CascadeType.MERGE)
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
    @ManyToOne(cascade=CascadeType.MERGE)
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
