package ru.team.up.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@Data
public class UserMessageDTO {

    /**
     * Полное имя владелеца сообщения
     */
    private String messageOwnerFullName;

    /**
     * Email владелеца сообщения
     */
    private String messageOwnerEmail;

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
     * Пользователи получившие сообщение еmail: полное имя
     */
    private Map<String, String > users;
}
