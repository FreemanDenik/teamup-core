package ru.team.up.teamupauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.team.up.teamupauth.entity.Role;
import ru.team.up.teamupauth.entity.User;


@SpringBootApplication
public class TeamupAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamupAuthApplication.class, args);
    }

}
