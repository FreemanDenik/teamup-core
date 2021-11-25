package ru.team.up.external.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"ru.team.up.external.impl", "ru.team.up.external.api"})
public class TeamupExternalApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamupExternalApiApplication.class, args);
    }
}
