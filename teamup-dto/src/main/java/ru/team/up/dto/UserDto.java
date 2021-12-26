package ru.team.up.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UserDto extends AccountDto{

    /**
     * Город
     */
    private String city;

    /**
     * Возраст
     */
    private LocalDate dateOfBirth;

    /**
     * Информация о пользователе
     */
    private String aboutUser;

    /**
     * Интересы пользователя
     */
    private Set<InterestsDto> userInterests;

    /**
     * Подписчики пользователя
     */
    private Set<UserDto> subscribers;


    /**
     * Мероприятия в которых участвует пользователь
     */
    private Set<EventDto>  userEvent;

    /**
     * Сообщения пользователя
     */
    private Set<UserMessageDto>  userMessages;
}
