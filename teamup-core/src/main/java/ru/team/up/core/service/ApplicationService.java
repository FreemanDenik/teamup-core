package ru.team.up.core.service;

import ru.team.up.core.entity.Application;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;

import java.util.List;

public interface ApplicationService {
    List<Application> getAllApplicationsByEventId(Long id);

    List<Application> getAllApplicationsByUserId(Long id);

    Application getApplication(Long id);

    Application saveApplication(Application application, User user);

    void deleteApplication(Long id);
}
