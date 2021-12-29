package ru.team.up.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class UserMessageDto {
    /**
     * Первичный ключ
     */
    private Long id;

    /**
     * Владелец сообщения
     */
    private UserDto messageOwner;

    /**
     * Текст сообщения
     */
    private String message;

    /**
     * Статус сообщения
     */
    private String status;

    /**
     * Время создания сообщения
     */
    private LocalDateTime messageCreationTime;

    /**
     * Время прочтения сообщения
     */
    private LocalDateTime messageReadTime;

    /**
     * Пользователи получившие сообщение
     */
    private Set<UserDto> users;
}
