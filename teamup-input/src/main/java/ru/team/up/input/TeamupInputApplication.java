package ru.team.up.input;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ru.team.up.core", "ru.team.up.input"})
public class TeamupInputApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamupInputApplication.class, args);
    }
}
