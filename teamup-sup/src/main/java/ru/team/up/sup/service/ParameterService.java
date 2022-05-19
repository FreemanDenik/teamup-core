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
    /*--------------AUTH module--------------*/
    SupParameter<Boolean> loginEnabled = new SupParameter<>(
            "TEAMUP_CORE_LOGIN_ENABLED",
            true);
    SupParameter<Boolean> loginByGoogleEnabled = new SupParameter<>(
            "TEAMUP_CORE_LOGIN_BY_GOOGLE_ENABLED",
            true);
    SupParameter<Boolean> registrationEnabled = new SupParameter<>(
            "TEAMUP_CORE_REGISTRATION_ENABLED",
            true);
    SupParameter<Boolean> printWelcomePageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_WELCOME_PAGE_ENABLED",
            true);
    SupParameter<Boolean> printAdminPageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_ADMIN_PAGE_ENABLED",
            true);
    SupParameter<Boolean> chooseRoleEnabled = new SupParameter<>(
            "TEAMUP_CORE_CHOOSE_ROLE_ENABLED",
            true);
    SupParameter<Boolean> printModeratorPageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_MODERATOR_PAGE_ENABLED",
            true);
    SupParameter<Boolean> oauth2regUserEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_OAUTH_2_REG_USER_ENABLED",
            true);
    SupParameter<Boolean> printRegistrationPageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_REGISTRATION_PAGE_ENABLED",
            true);
    SupParameter<Boolean> printUserPageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_USER_PAGE_ENABLED",
            true);
    /*--------------INPUT module--------------*/
    SupParameter<Boolean> getEventByIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_EVENT_BY_ID_ENABLED",
            true);
    SupParameter<Boolean> getUserByIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_USER_BY_ID_ENABLED",
            true);
    SupParameter<Boolean> getAllEventsEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_EVENTS_ENABLED",
            true);
    SupParameter<Boolean> createEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_CREATE_EVENT_ENABLED",
            true);
    SupParameter<Boolean> updateNumberOfParticipantsEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_NUMBER_OF_PARTICIPANTS_ENABLED",
            true);
    SupParameter<Boolean> getOneEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ONE_EVENT_ENABLED",
            true);
    SupParameter<Boolean> updateEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_EVENT_ENABLED",
            true);
    SupParameter<Boolean> deleteAdminEnabled = new SupParameter<>(
            "TEAMUP_CORE_DELETE_ADMIN_ENABLED",
            true);
    SupParameter<Boolean> sendApplicationEnabled = new SupParameter<>(
            "TEAMUP_CORE_SEND_APPLICATION_ENABLED",
            true);
    SupParameter<Boolean> getAllApplicationsByEventIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_APPLICATION_BY_EVENT_ENABLED",
            true);
    SupParameter<Boolean> getAllApplicationsByUserIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_APPLICATION_BY_USER_ID_ENABLED",
            true);
    SupParameter<Boolean> getAllUsersEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_USERS_ENABLED",
            true);
    SupParameter<Boolean> createUserEnabled = new SupParameter<>(
            "TEAMUP_CORE_CREATE_USER_ENABLED",
            true);
    SupParameter<Boolean> getUserByIdPrivateEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_USER_BY_ID_PRIVATE_ENABLED",
            true);
    SupParameter<Boolean> updateUserEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_USER_ENABLED",
            true);
    SupParameter<Boolean> deleteUserEnabled = new SupParameter<>(
            "TEAMUP_CORE_DELETE_USER_ENABLED",
            true);
    SupParameter<Boolean> getAllModeratorsEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_MODERATORS_ENABLED",
            true);
    SupParameter<Boolean> createModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_CREATE_MODERATOR_ENABLED",
            true);
    SupParameter<Boolean> getOneModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ONE_MODERATOR_ENABLED",
            true);
    SupParameter<Boolean> updateModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_MODERATOR_ENABLED",
            true);
    SupParameter<Boolean> deleteModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_DELETE_MODERATOR_ENABLED",
            true);
    SupParameter<Boolean> getAssignedEventsOfModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ASSIGNED_EVENTS_OF_MODERATOR_ENABLED",
            true);
    SupParameter<Boolean> sendEmailUserMessageEnabled = new SupParameter<>(
            "TEAMUP_CORE_SEND_EMAIL_USER_MESSAGE_ENABLED",
            true);
    SupParameter<Boolean> getAllAdminsEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_ADMINS_ENABLED",
            true);
    SupParameter<Boolean> createAdminEnabled = new SupParameter<>(
            "TEAMUP_CORE_CREATE_ADMIN_ENABLED",
            true);
    SupParameter<Boolean> getOneAdminEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ONE_ADMIN_ENABLED",
            true);
    SupParameter<Boolean> updateAdminEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_ADMIN_ENABLED",
            true);
    SupParameter<Boolean> deleteAdminFromAdminControllerEnabled = new SupParameter<>(
            "TEAMUP_CORE_DELETE_ADMIN_FROM_ADMIN_CONTROLLER_ENABLED",
            true);
    /*--------------CORE module--------------*/
    SupParameter<Integer> countReturnCity = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY",
            10);

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