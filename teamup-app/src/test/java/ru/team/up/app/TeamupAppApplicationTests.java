package ru.team.up.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import ru.team.up.auth.service.UserService;
import ru.team.up.core.repositories.StatusRepository;
import ru.team.up.external.impl.service.GeoService;
import ru.team.up.input.service.ValidatorService;


import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
class TeamupAppApplicationTests {

    @Test
    void contextLoads() {
    }

}
