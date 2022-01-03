package ru.team.up.teamup.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.team.up.teamup.repositories.DataRepository;

@Service
public class DataServiceImpl {

    private DataRepository repository;

    public DataServiceImpl(DataRepository repository) {
        this.repository = repository;
    }

    public Data saveMessage(Data data) {
        return repository.save(data);
    }
}
