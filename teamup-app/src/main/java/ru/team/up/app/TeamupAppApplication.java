package ru.team.up.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ru.team.up*")
@PropertySource({
        "classpath:db.properties",
        "classpath:auth.properties",
        "classpath:external.properties"
})
public class TeamupAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamupAppApplication.class, args);
    }

}
