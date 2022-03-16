package ru.team.up.input.service;

import ru.team.up.core.entity.Interests;

import java.util.List;

/**
 * Сервис для поиска интересов
 */
public interface InterestServiceRest {

    /**
     * Метод получения всех интересов
     *
     * @return Список интересов
     */
    List<Interests> getAllInterests();

    /**
     * Метод получения интереса по Id
     *
     * @param id Идентификатор интереса
     * @return Интерес по заданному Id
     */
    Interests getInterestById(Long id);
}
