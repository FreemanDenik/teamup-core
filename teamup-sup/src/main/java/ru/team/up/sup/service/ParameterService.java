package ru.team.up.sup.service;

import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.entity.SupParameter;

import java.util.List;

/**
 * Интерфейс сервиса для получения локальных параметров и установки значений по умолчанию
 */
public interface ParameterService {

    /**
     * Параметры модуля по умолчанию
     */
    SupParameter<Boolean> getEventByIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_EVENT_BY_ID_ENABLED",
            false);
    SupParameter<Boolean> getUserByIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_USER_BY_ID_ENABLED",
            false);
    SupParameter<Integer> countReturnCity = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY",
            1);

    /**
     * Получение листа текущих параметров из кэша
     */
    List<SupParameterDto<?>> getAll();

    /**
     * Добавление или перезапись параметра
     */
    void addParam(SupParameterDto<?> parameter);

    /**
     * Поиск параметра по имени
     */
    SupParameterDto<?> getParamByName(String name);

}