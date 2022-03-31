package ru.team.up.sup.service;

import ru.team.up.dto.SupParameterDto;

import java.util.List;

/**
 * Интерфейс сервиса для получения локальных парамтеров
 */
public interface ParameterService {

    List<SupParameterDto<?>> getAll();

    void addParam(SupParameterDto<?> parameter);

    SupParameterDto<?> getParamByName(String name);
}