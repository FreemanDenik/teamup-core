package ru.team.up.input;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ru.team.up.core", "ru.team.up.input.entity", "ru.team.up.input.exception", "ru.team.up.input.payload", "ru.team.up.input.service", "ru.team.up.input.wordmatcher", "ru.team.up.input.controlle"})
public class TeamupInputApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamupInputApplication.class, args);
    }
}
