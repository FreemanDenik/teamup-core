package ru.team.up.sup.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.repository.SupRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SupServiceImpl implements SupService {
    private SupRepository supRepository;

    @Override
    public List<SupParameterDto> getListParameters() {
        return supRepository.findAll();
    }
}