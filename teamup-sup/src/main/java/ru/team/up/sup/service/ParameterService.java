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
            true, false);
    SupParameter<Boolean> loginByGoogleEnabled = new SupParameter<>(
            "TEAMUP_CORE_LOGIN_BY_GOOGLE_ENABLED",
            true, false);
    SupParameter<Boolean> registrationEnabled = new SupParameter<>(
            "TEAMUP_CORE_REGISTRATION_ENABLED",
            true, false);
    SupParameter<Boolean> printWelcomePageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_WELCOME_PAGE_ENABLED",
            true, false);
    SupParameter<Boolean> printAdminPageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_ADMIN_PAGE_ENABLED",
            true, false);
    SupParameter<Boolean> chooseRoleEnabled = new SupParameter<>(
            "TEAMUP_CORE_CHOOSE_ROLE_ENABLED",
            true, false);
    SupParameter<Boolean> printModeratorPageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_MODERATOR_PAGE_ENABLED",
            true, false);
    SupParameter<Boolean> oauth2regUserEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_OAUTH_2_REG_USER_ENABLED",
            true, false);
    SupParameter<Boolean> printRegistrationPageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_REGISTRATION_PAGE_ENABLED",
            true, false);
    SupParameter<Boolean> printUserPageEnabled = new SupParameter<>(
            "TEAMUP_CORE_PRINT_USER_PAGE_ENABLED",
            true, false);
    /*--------------INPUT module--------------*/
    SupParameter<Boolean> getEventByIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_EVENT_BY_ID_ENABLED",
            true, false);
    SupParameter<Boolean> getUserByIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_USER_BY_ID_ENABLED",
            true, false);
    SupParameter<Boolean> getAllEventsPrivateEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_EVENTS_ENABLED",
            true, false);
    SupParameter<Boolean> createEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_CREATE_EVENT_ENABLED",
            true, false);
    SupParameter<Boolean> updateNumberOfParticipantsEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_NUMBER_OF_PARTICIPANTS_ENABLED",
            true, false);
    SupParameter<Boolean> getOneEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ONE_EVENT_ENABLED",
            true, false);
    SupParameter<Boolean> updateEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_EVENT_ENABLED",
            true, false);
    SupParameter<Boolean> deleteAdminEnabled = new SupParameter<>(
            "TEAMUP_CORE_DELETE_ADMIN_ENABLED",
            true, false);
    SupParameter<Boolean> sendApplicationEnabled = new SupParameter<>(
            "TEAMUP_CORE_SEND_APPLICATION_ENABLED",
            true, false);
    SupParameter<Boolean> getAllApplicationsByEventIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_APPLICATION_BY_EVENT_ENABLED",
            true, false);
    SupParameter<Boolean> getAllApplicationsByUserIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_APPLICATION_BY_USER_ID_ENABLED",
            true, false);
    SupParameter<Boolean> getAllUsersEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_USERS_ENABLED",
            true, false);
    SupParameter<Boolean> createUserEnabled = new SupParameter<>(
            "TEAMUP_CORE_CREATE_USER_ENABLED",
            true, false);
    SupParameter<Boolean> getUserByIdPrivateEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_USER_BY_ID_PRIVATE_ENABLED",
            true, false);
    SupParameter<Boolean> updateUserEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_USER_ENABLED",
            true, false);
    SupParameter<Boolean> deleteUserEnabled = new SupParameter<>(
            "TEAMUP_CORE_DELETE_USER_ENABLED",
            true, false);
    SupParameter<Boolean> getAllModeratorsEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_MODERATORS_ENABLED",
            true, false);
    SupParameter<Boolean> createModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_CREATE_MODERATOR_ENABLED",
            true, false);
    SupParameter<Boolean> getOneModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ONE_MODERATOR_ENABLED",
            true, false);
    SupParameter<Boolean> updateModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_MODERATOR_ENABLED",
            true, false);
    SupParameter<Boolean> deleteModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_DELETE_MODERATOR_ENABLED",
            true, false);
    SupParameter<Boolean> getAssignedEventsOfModeratorEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ASSIGNED_EVENTS_OF_MODERATOR_ENABLED",
            true, false);
    SupParameter<Boolean> sendEmailUserMessageEnabled = new SupParameter<>(
            "TEAMUP_CORE_SEND_EMAIL_USER_MESSAGE_ENABLED",
            true, false);
    SupParameter<Boolean> getAllAdminsEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ALL_ADMINS_ENABLED",
            true, false);
    SupParameter<Boolean> createAdminEnabled = new SupParameter<>(
            "TEAMUP_CORE_CREATE_ADMIN_ENABLED",
            true, false);
    SupParameter<Boolean> getOneAdminEnabled = new SupParameter<>(
            "TEAMUP_CORE_GET_ONE_ADMIN_ENABLED",
            true, false);
    SupParameter<Boolean> updateAdminEnabled = new SupParameter<>(
            "TEAMUP_CORE_UPDATE_ADMIN_ENABLED",
            true, false);
    SupParameter<Boolean> deleteAdminFromAdminControllerEnabled = new SupParameter<>(
            "TEAMUP_CORE_DELETE_ADMIN_FROM_ADMIN_CONTROLLER_ENABLED",
            true, false);
    /*--------------CORE module--------------*/
    SupParameter<Integer> countReturnCity = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY",
            10, false);
    SupParameter<Boolean> getCityByNameEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY_FOR_TITLE",
            true, false);
    SupParameter<Boolean> getCityByNameInSubjectEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY_FOR_TITLE_IN_SUBJECT",
            true, false);
    SupParameter<Boolean> getAllCitiesEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_ALL_CITY",
            true, false);
    SupParameter<Boolean> getSomeCitiesByNameEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CITY_SUITABLE_FOR_TITLE",
            true, false);
    SupParameter<Boolean> getIsAvailableUsernameEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_AVAILABILITY_CHECK_SURNAME",
            true, false);
    SupParameter<Boolean> getIsAvailableEmailEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_AVAILABILITY_CHECK_EMAIL",
            true, false);
    SupParameter<Boolean> getAllEventsEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_ALL_EVENTS",
            true, false);
    SupParameter<Boolean> getAllEventByCityEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_ALL_EVENTS_BY_CITY",
            true, false);
    SupParameter<Boolean> getFindEventsByNameEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_EVENTS_BY_NAME",
            true, false);
    SupParameter<Boolean> getFindEventsByAuthorEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_EVENTS_BY_AUTHOR",
            true, false);
    SupParameter<Boolean> getFindEventsByTypeEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_EVENTS_BY_TYPE",
            true, false);
    SupParameter<Boolean> getCreateEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_CREATE_EVENT",
            true, false);
    SupParameter<Boolean> getUpdateEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETURN_UPDATE_EVENT",
            true, false);
    SupParameter<Boolean> getDeleteEventEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_DELETE_EVENT",
            true, false);
    SupParameter<Boolean> getAddEventParticipantEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_ADD_EVENT_PARTICIPANT",
            true, false);
    SupParameter<Boolean> getDeleteEventParticipantEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_DELETE_EVENT_PARTICIPANT",
            true, false);
    SupParameter<Boolean> getInterestsUserByIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETUTN_INTEREST_USERS_BY_ID",
            true, false);
    SupParameter<Boolean> getEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_RETUTN_ALL_MODULE_PARAMETERS",
            true, false);
    SupParameter<Boolean> getUserByEmailEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_USER_BY_EMAIL",
            true, false);
    SupParameter<Boolean> getUserByUsernameEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_USER_BY_USERNAME",
            true, false);
    SupParameter<Boolean> getUsersListEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_ALL_USERS",
            true, false);
    SupParameter<Boolean> getEventsByOwnerIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_EVENTS_BY_ID_USERS",
            true, false);
    SupParameter<Boolean> getEventsBySubscriberIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_EVENTS_BY_SUBSCRIBER_ID_USER",
            true, false);
    SupParameter<Boolean> getUpdateUserEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_UPDATE_USER",
            true, false);
    SupParameter<Boolean> getDeleteUserByIdEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_DELETE_USER",
            true, false);
    SupParameter<Boolean> getTopUsersListInCityEnabled = new SupParameter<>(
            "TEAMUP_CORE_COUNT_TOP_USERS_IN_CITY",
            true, false);
    SupParameter<Integer> getModeratorDisconnectTimeout = new SupParameter<>(
            "TEAMUP_CORE_MODERATOR_DISCONNECT_TIMEOUT",
            30, false);
    SupParameter<Integer> getModeratorEventLimitation = new SupParameter<>(
            "TEAMUP_CORE_MODERATOR_EVENT_LIMITATION",
            3, false);
    SupParameter<String> getSupDefaultParamURL = new SupParameter<>(
            "TEAMUP_CORE_DEFAULT_PARAM_URL",
            "http://localhost:8083/public/api/update/TEAMUP_CORE/", false);
    SupParameter<String> getNotificationUriHost = new SupParameter<>(
            "TEAMUP_CORE_NOTIFICATION_URI_HOST",
            "http://localhost:8085", false
    );

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