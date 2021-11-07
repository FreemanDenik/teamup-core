package ru.team.up.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.auth.service.UserService;
import ru.team.up.external.impl.service.GeoService;
import ru.team.up.input.service.ValidatorService;

import javax.persistence.EntityManagerFactory;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@SpringBootTest
class TeamupAppApplicationTests {

    @Autowired
    private EntityManagerFactory entityManagerFactory;
    @Autowired
    private ValidatorService validatorService;
    @Autowired
    private GeoService geoService;
    @Autowired
    private UserService userService;
    private
    @Test
    void contextLoads() {
    }

    @Test
    public void TestExternalContext() {
        assertNotNull(geoService);
    }

    @Test
    public void TestCoreContext() {
        assertNotNull(entityManagerFactory);
    }

    @Test
    public void TestInputC() {
        assertNotNull(validatorService);
    }

    @Test
    public void TestAuthContext() {
        assertNotNull(userService);
    }
}
