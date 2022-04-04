package ru.team.up.sup.service;

import ru.team.up.dto.SupParameterDto;

public class DefaultSupParametrValue {

    private final String TEST_NAME = "Test Name";
    private final String CIA_MEETING_FLAG = "AgentFreedom";
    private final int MONETIZATION_LEVEL = 0;
    private final Boolean TEAMUP_CORE_GET_USER_BY_ID_ENABLED = true;


    private String getTEST_NAME() {
        return TEST_NAME;
    }
}
