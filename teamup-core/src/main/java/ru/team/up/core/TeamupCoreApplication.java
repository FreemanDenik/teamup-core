package ru.team.up.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:db.properties")
public class TeamupCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamupCoreApplication.class, args);
    }

}
