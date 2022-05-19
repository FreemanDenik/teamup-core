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
    SupParameter<Boolean> getAllEventsEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_ALL_EVENTS",
            true);
    SupParameter<Boolean> getAllEventByCityEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_ALL_EVENTS_BY_CITY",
            true);
    SupParameter<Boolean> getFindEventsByNameEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_EVENTS_BY_NAME",
            true);
    SupParameter<Boolean> getFindEventsByAuthorEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_EVENTS_BY_AUTHOR",
            true);
    SupParameter<Boolean> getFindEventsByTypeEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_EVENTS_BY_TYPE",
            true);
    SupParameter<Boolean> getCreateEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CREATE_EVENT",
            true);
    SupParameter<Boolean> getUpdateEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_UPDATE_EVENT",
            true);
    SupParameter<Boolean> getDeleteEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_DELETE_EVENT",
            true);
    SupParameter<Boolean> getAddEventParticipantEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_ADD_EVENT_PARTICIPANT",
            true);
    SupParameter<Boolean> getDeleteEventParticipantEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_DELETE_EVENT_PARTICIPANT",
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