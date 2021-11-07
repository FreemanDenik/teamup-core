package ru.team.up.external.impl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:external.properties")
public class TeamupExternalImpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamupExternalImpApplication.class, args);
    }

}
