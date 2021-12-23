package ru.team.up.dto;

import lombok.Data;

@Data
public class AccountCreateDTO {
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
     * Пароль
     */
    private String password;
}
