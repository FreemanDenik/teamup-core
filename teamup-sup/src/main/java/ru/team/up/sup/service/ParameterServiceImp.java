package ru.team.up.sup.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.entity.SupParameter;
import ru.team.up.sup.repository.ParameterDao;

import java.util.List;
import java.util.Set;

@Data
@Slf4j
@PropertySource("classpath:defaultParameters.properties")
@Service
public class ParameterServiceImp implements ParameterService {

    private final ParameterDao parameterDao;
    private Set<SupParameter<?>> parameterSet = Set.of(
            getEventByIdEnabled,
            getUserByIdEnabled,
            countReturnCity);

    @Value("${getEventByIdEnabled}")
    private Boolean defaultGetEventByIdEnabled;
    @Value("${getUserByIdEnabled}")
    private Boolean defaultGetUserByIdEnabled;
    @Value("${countReturnCity}")
    private Integer defaultCountReturnCity;

    @Override
    public List<SupParameterDto<?>> getAll() {
        return parameterDao.findAll();
    }

    @Override
    public SupParameterDto<?> getParamByName(String name) {
        return parameterDao.findByName(name);
    }

    @Override
    public void addParam(SupParameterDto<?> parameter) {
        parameterDao.add(parameter);
        load(parameter);
    }

    @Override
    public void load(SupParameterDto dto) {
        log.debug("Load enter");
        for (SupParameter parameter : parameterSet) {
            if (dto.getParameterName().equals(parameter.getName())) {
                log.debug("Параметр {} со значением {}", parameter.getName(), parameter.getValue());
                parameter.setValue(parameterDao.findByName(parameter.getName()).getParameterValue());
                log.debug("Теперь имеет значение {}", parameter.getValue());
            }
        }
        log.debug("Load exit");
    }
}