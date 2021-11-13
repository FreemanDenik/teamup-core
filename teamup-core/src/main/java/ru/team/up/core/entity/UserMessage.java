package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "STATUS")
    @ManyToOne
    private Status status;

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
}
