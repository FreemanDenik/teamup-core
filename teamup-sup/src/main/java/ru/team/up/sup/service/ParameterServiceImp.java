package ru.team.up.sup.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.repository.ParameterDao;

import java.util.List;

@Data
@Service
public class ParameterServiceImp implements ParameterService {

    private final ParameterDao parameterDao;

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
    }
}