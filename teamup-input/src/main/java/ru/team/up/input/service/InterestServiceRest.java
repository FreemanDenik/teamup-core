package ru.team.up.input.service;

import ru.team.up.core.entity.Interests;

import javax.transaction.Transactional;
import java.util.List;

public interface InterestServiceRest {
    List<Interests> getAllInterests();

    List<Interests> getInterestById(Long id);
}
