package ru.team.up.dto;

import java.util.Map;
import java.util.Set;

public class UserDTO {
    /**
     * Имя
     */
    private String name;

    /**
     * Фамилия
     */
    private String lastName;

    /**
     * Отчество
     */
    private String middleName;

    /**
     * Электронная почта
     */
    private String email;

    /**
     * Город
     */
    private String city;

    /**
     * Возраст
     */
    private Integer age;

    /**
     * Информация о пользователе
     */
    private String aboutUser;

    /**
     * Интересы пользователя
     */
    private Set<String> userInterests;

    /**
     * Подписчики пользователя email:ФИО
     */
    private Map<String, String> emailFullNameSubscribers;


    /**
     * Мероприятия в которых участвует пользователь
     */
    private Set<Map<String,String>>  event;

    /**
     * Сообщения пользователя
     */
    private Set<Map<String,String>>  userMessages;
}
