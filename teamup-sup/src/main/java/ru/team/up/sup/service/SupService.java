package ru.team.up.sup.service;


import ru.team.up.dto.SupParameterDto;

import java.util.List;

/**
 * @author Stepan Glushchenko
 * Интерфейс сервиса для отправки парамтеров в kafka
 */

public interface SupService {
    /**
     * Получение списка параметров из брокера сообщений
     *
     * @return возвращает List из объектов SupParameterDto
     */
    List<SupParameterDto> getListParameters();
}