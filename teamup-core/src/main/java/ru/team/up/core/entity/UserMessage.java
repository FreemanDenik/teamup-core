package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Alexey Tkachenko
 *
 * Сущность сообщения для пользователя
 */

@Entity
@Table(name = "USER_MESSAGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@ToString
public class UserMessage {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Владелец сообщения
     */
    @JoinColumn(name = "MESSAGE_OWNER")
    @ManyToOne
    private User messageOwner;

    /**
     * Сообщение
     */
    @Column(name = "MESSAGE")
    private String message;

    /**
     * Статус сообщения
     */
    @Column(name = "STATUS")
    private String status;

    /**
     * Время создания сообщения
     */
    @Column(name = "MESSAGE_CREATION_TIME")
    private LocalDateTime messageCreationTime;

    /**
     * Время прочтения сообщения
     */
    @Column(name = "MESSAGE_READ_TIME")
    private LocalDateTime messageReadTime;

    /**
     * Пользователи получившие сообщение
     */
    @ManyToMany
    @JoinTable(name = "USER_ACCOUNT_MESSAGES",
            joinColumns = @JoinColumn(name = "MESSAGE_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    @ToString.Exclude
    private Set<User> users;
}
