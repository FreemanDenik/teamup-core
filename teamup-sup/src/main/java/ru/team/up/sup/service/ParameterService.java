package ru.team.up.sup.service;

import ru.team.up.dto.SupParameterDto;

/**
 * Интерфейс сервиса для получения локальных парамтеров
 */
public interface ParameterService {

    SupParameterDto<?> getParamByName(String name);
}