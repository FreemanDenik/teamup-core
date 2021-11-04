package ru.team.up.core.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Родительская сущность для пользователя, админа, модератора
 */
@MappedSuperclass
public class Account {

    /**
     * Уникальный идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя
     */
    @Column(name = "NAME", nullable = false)
    private String name;

    /**
     * Фамилия
     */
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    /**
     * Отчество
     */
    @Column(name = "MIDDLE_NAME")
    private String middleName;

    /**
     * Логин
     */
    @Column(name = "LOGIN", nullable = false)
    private String login;

    /**
     * Электронная почта
     */
    @Column(name = "EMAIL", nullable = false)
    private String email;

    /**
     * Пароль
     */
    @Column(name = "PASSWORD", nullable = false)
    private String password;

    /**
     * Время создания аккаунта
     */
    @Column(name = "ACCOUNT_CREATED_TIME", nullable = false)
    private String accountCreatedTime;

    /**
     * Время последней активности
     */
    @Column(name = "LAST_ACCOUNT_ACTIVITY", nullable = false)
    private String lastAccountActivity;
}
