package ru.team.up.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan({"ru.team.up"})
@EnableJpaRepositories("ru.team.up.core.repositories")
@EntityScan("ru.team.up.core")
@PropertySource({
        "classpath:db.properties",
        "classpath:auth.properties",
        "classpath:sup.properties",
        "classpath:monitoring.properties",
        "classpath:email-service.properties"
})
@EnableWebMvc
public class TeamupAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamupAppApplication.class, args);
    }
}
