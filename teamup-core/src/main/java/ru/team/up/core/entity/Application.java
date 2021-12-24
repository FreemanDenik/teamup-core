package ru.team.up.core.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * Сущность заявка
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "APPLICATION")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "users"})
public class Application {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * Пользователь
     */
    @ManyToOne
    @JoinTable(name = "USER_ACCOUNT_APPLICATION",
            joinColumns = @JoinColumn(name = "APPLICATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private User user;

    /**
     * ID мероприятия
     */
    @ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinColumn(name="EVENT_ID")
    private Event event;

}
