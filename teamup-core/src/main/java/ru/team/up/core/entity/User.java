package ru.team.up.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "userInterests"})
public class User extends Account {

    /**
     * Город
     */
    @Column(name = "CITY")
    private String city;

    /**
     * Возраст
     */
    @Column(name = "AGE", nullable = false)
    private Integer age;

    /**
     * Информация о пользователе
     */
    @Column(name = "ABOUT_USER")
    private String aboutUser;

    /**
     * Интересы пользователя
     */
    @ManyToMany (cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinTable (name="USER_ACCOUNT_INTERESTS", joinColumns=@JoinColumn (name="USER_ID"),
            inverseJoinColumns=@JoinColumn(name="INTERESTS_ID"))
    @Column(name = "USER_INTERESTS")
    private Set<Interests> userInterests;
}
