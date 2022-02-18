package ru.team.up.input;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"ru.team.up.input", "ru.team.up.core"})
public class TeamupInputApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamupInputApplication.class, args);
    }
}
