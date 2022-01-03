package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Alexey Tkachenko
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
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "STATUS_ID")
    private Status status;

    /**
     * Тип сообщения
     */
    @Column(name = "MESSAGE_TYPE")
    @Enumerated(EnumType.STRING)
    private UserMessageType messageType;

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
