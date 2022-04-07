package ru.team.up.sup.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.team.up.dto.AppModuleNameDto;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.entity.SupParameter;
import ru.team.up.sup.repository.ParameterDao;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Data
@Slf4j
@Service
public class ParameterServiceImp implements ParameterService {

    private final ParameterDao parameterDao;
    private Set<SupParameter<?>> parameterSet = Set.of(
            getEventByIdEnabled,
            getUserByIdEnabled,
            countReturnCity);

    @PostConstruct
    private void init() {
        initialize();
    }

    @Override
    public void initialize() {
//        try (FileWriter writer = new FileWriter("./Parameters_2.json")) {
//            writer.write(getEventByIdEnabled.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        for (SupParameter parameter : parameterSet){
            parameterDao.add(SupParameterDto.builder()
                    .parameterName(parameter.getName())
                    .systemName(AppModuleNameDto.TEAMUP_CORE)
                    .parameterValue(parameter.getValue())
                    .build());
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File("./Parameters.json"), parameterDao.findAll());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        updateStaticField(parameter);
    }

    @Override
    public void updateStaticField(SupParameterDto dto) {
        log.debug("Method load enter");
        for (SupParameter parameter : parameterSet) {
            if (dto.getParameterName().equals(parameter.getName())) {
                log.debug("Параметр {} со значением {}", parameter.getName(), parameter.getValue());
                parameter.setValue(parameterDao.findByName(parameter.getName()).getParameterValue());
                log.debug("Теперь имеет значение {}", parameter.getValue());
                break;
            }
        }
        log.debug("Method load exit");
    }
}