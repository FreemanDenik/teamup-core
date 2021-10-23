package ru.team.up.input;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.team.up.input.service.Validator;
import ru.team.up.input.service.impl.ValidatorImpl;

@SpringBootApplication
public class TeamupInputApplication {

    public static void main(String[] args) {
        SpringApplication.run(
                TeamupInputApplication.class, args);
    }

}
