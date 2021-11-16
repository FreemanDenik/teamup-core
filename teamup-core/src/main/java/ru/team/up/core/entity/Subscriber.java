package ru.team.up.core.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
@Table(name = "SUBSCRIBER")
public class Subscriber extends User {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Пользователи на которых подписан подписчик
     */
    @ManyToMany
    @JoinTable(name = "USER_ACCOUNT_SUBSCRIBERS",
            joinColumns = @JoinColumn(name = "SUBSCRIBER_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> users;
}
