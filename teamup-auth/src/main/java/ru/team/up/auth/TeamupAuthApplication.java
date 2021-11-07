package ru.team.up.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:auth.properties")
public class TeamupAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamupAuthApplication.class, args);
    }

}
