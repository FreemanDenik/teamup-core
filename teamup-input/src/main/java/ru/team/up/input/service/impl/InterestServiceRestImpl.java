package ru.team.up.input.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.Interests;
import ru.team.up.core.repositories.InterestsRepository;
import ru.team.up.input.service.InterestServiceRest;

import javax.transaction.Transactional;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InterestServiceRestImpl implements InterestServiceRest {

    private final InterestsRepository interestsRepository;

    @Override
    @Transactional
    public List<Interests> getAllInterests() {
        return interestsRepository.findAll();
    }

    @Override
    @Transactional
    public Interests getInterestById(Long id) {
        return interestsRepository.getInterestsById(id);
    }
}