package ru.team.up.sup.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.team.up.dto.AppModuleNameDto;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.repository.ParameterDao;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Slf4j
@Component
public class DefaultParameter {

    private ParameterDao parameterDao;

    @Autowired
    public DefaultParameter (ParameterDao parameterDao){
        this.parameterDao = parameterDao;
    }

     private final SupParameterDto <?> testName = SupParameterDto.builder()
             .parameterName("TestName")
             .parameterValue("TestName")
             .isDeleted(false)
             .updateTime(LocalDateTime.now())
             .systemName(AppModuleNameDto.TEAMUP_CORE)
             .build();

    private final SupParameterDto <?> CIAMetingFlag = SupParameterDto.builder()
            .parameterName("TestCIAMetingFlag")
            .parameterValue("AgentFreedom")
            .isDeleted(false)
            .updateTime(LocalDateTime.now())
            .systemName(AppModuleNameDto.TEAMUP_CORE)
            .build();

    private final SupParameterDto <?> MonetizationLevel = SupParameterDto.builder()
            .parameterName("TestMonetizationLevel")
            .parameterValue(0)
            .isDeleted(false)
            .updateTime(LocalDateTime.now())
            .systemName(AppModuleNameDto.TEAMUP_CORE)
            .build();

    private final SupParameterDto <?> getUserByIdEnabled = SupParameterDto.builder()
            .parameterName("TEAMUP_CORE_GET_USER_BY_ID_ENABLED")
            .parameterValue(false)
            .isDeleted(false)
            .updateTime(LocalDateTime.now())
            .systemName(AppModuleNameDto.TEAMUP_CORE)
            .build();

    private final SupParameterDto <?> countReturnCity = SupParameterDto.builder()
            .parameterName("TEAMUP_CORE_COUNT_RETURN_CITY")
            .parameterValue(10)
            .isDeleted(false)
            .updateTime(LocalDateTime.now())
            .systemName(AppModuleNameDto.TEAMUP_CORE)
            .build();

    private final SupParameterDto <?> getEventByIdEnabled = SupParameterDto.builder()
            .parameterName("TEAMUP_CORE_GET_EVENT_BY_ID_ENABLED")
            .parameterValue(10)
            .isDeleted(false)
            .updateTime(LocalDateTime.now())
            .systemName(AppModuleNameDto.TEAMUP_CORE)
            .build();


     @PostConstruct
    public void init () {
         parameterDao.add(testName);
         parameterDao.add(CIAMetingFlag);
         parameterDao.add(getEventByIdEnabled);
         parameterDao.add(getUserByIdEnabled);
         parameterDao.add(MonetizationLevel);
         parameterDao.add(countReturnCity);
         log.debug("Добавлены параметры по умолчанию");
    }
}

