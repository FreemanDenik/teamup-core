package ru.team.up.dto;


import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Объект для передачи данных пользователя, админа, модератора
 */

@Data
@SuperBuilder
public class AccountDto {
    /**
     * Первичный ключ
     */
    private Long id;

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
