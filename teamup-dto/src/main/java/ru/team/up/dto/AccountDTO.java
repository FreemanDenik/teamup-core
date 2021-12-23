package ru.team.up.dto;


import lombok.Data;

/**
 * Объект для передачи данных пользователя, админа, модератора
 */
@Data
public class AccountDTO {
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

}
