package ru.team.up.sup.service;

import java.util.ServiceLoader;

public class SupParametrService implements DefSupParametrService{

    private ServiceLoader<SupParametrService> loader = ServiceLoader.load(SupParametrService.class);

    @Override
    public String getValue() {
        return "SupParametrService";

    }


}
