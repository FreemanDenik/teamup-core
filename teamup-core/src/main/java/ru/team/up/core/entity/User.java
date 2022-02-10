package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

/**
 * Сущность пользователь
 */
@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER_ACCOUNT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "userInterests", "userMessages", "userEvent"})
public class User extends Account {


    /**
     * Город
     */
    @Column(name = "CITY")
    private String city;

    /**
     * Возраст
     */
    @Column(name = "BIRTHDAY", nullable = false)
    private LocalDate birthday;

    /**
     * Информация о пользователе
     */
    @Column(name = "ABOUT_USER")
    private String aboutUser;

    /**
     * Интересы пользователя
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ACCOUNT_INTERESTS", joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "INTERESTS_ID"))
    @Column(name = "USER_INTERESTS")
    private Set<Interests> userInterests;

    /**
     * Подписчики пользователя
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ACCOUNT_SUBSCRIBERS", joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "SUBSCRIBER_ID"))
    @Column(name = "USER_SUBSCRIBERS")
    private Set<User> subscribers;


    /**
     * Мероприятия в которых участвует пользователь
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ACCOUNT_EVENT", joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "EVENT_ID"))
    private Set<Event> userEvent;

    /**
     * Сообщения пользователя
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_ACCOUNT_MESSAGES", joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "MESSAGE_ID"))
    @Column(name = "USER_MESSAGES")
    private Set<UserMessage> userMessages;

    /**
     * Добавляет новое сообщение в список сообщений пользователя
     * @param userMessage
     */
    public void addUserMessage(UserMessage userMessage) {
        userMessages.add(userMessage);
    }

}
