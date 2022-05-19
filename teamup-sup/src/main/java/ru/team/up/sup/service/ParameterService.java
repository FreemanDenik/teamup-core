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
            true);
    SupParameter<Boolean> getUserByIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_USER_BY_ID_ENABLED",
            true);
    SupParameter<Integer> countReturnCity = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY",
            10);
    SupParameter<Boolean> getCityByNameEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY_FOR_TITLE",
            true);
    SupParameter<Boolean> getCityByNameInSubjectEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY_FOR_TITLE_IN_SUBJECT",
            true);
    SupParameter<Boolean> getAllCitiesEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_ALL_CITY",
            true);
    SupParameter<Boolean> getSomeCitiesByNameEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY_SUITABLE_FOR_TITLE",
            true);
    SupParameter<Boolean> getIsAvailableUsernameEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_AVAILABILITY_CHECK_SURNAME",
            true);
    SupParameter<Boolean> getIsAvailableEmailEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_AVAILABILITY_CHECK_EMAIL",
            true);

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