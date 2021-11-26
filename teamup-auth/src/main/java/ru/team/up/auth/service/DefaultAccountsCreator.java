package ru.team.up.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.entity.Role;
import ru.team.up.core.entity.User;
import ru.team.up.core.repositories.AdminRepository;
import ru.team.up.core.repositories.ModeratorRepository;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DefaultAccountsCreator {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;

    @Autowired
    public DefaultAccountsCreator(UserRepository userRepository,  AdminRepository adminRepository, ModeratorRepository moderatorRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.moderatorRepository = moderatorRepository;
    }

    @Bean("usercr")
    public void userCreator() {
        userRepository.save(User.builder()
                .id(1L)
                .name("Andrey")
                .lastName("Tikhonov")
                .middleName("Vladimirovich")
                .login("atata256")
                .password(BCrypt.hashpw("1234", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("user@mail.ru")
                .age(12)
                .role(Role.ROLE_USER.name())
                .build());

        adminRepository.save(Admin.builder()
                .id(2L)
                .name("admin")
                .lastName("admin")
                .middleName("admin")
                .login("admin")
                .password(BCrypt.hashpw("admin", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("admin@mail.ru").role(Role.ROLE_ADMIN.name())
                .build());


        moderatorRepository.save(Moderator.builder()
                .id(3L)
                .name("moderator")
                .lastName("moderator")
                .middleName("moderator")
                .login("moderator")
                .password(BCrypt.hashpw("moderator", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("moderator@mail.ru")
                .role(Role.ROLE_MODERATOR.name())
                .amountOfCheckedEvents(2L)
                .amountOfClosedRequests(2L)
                .amountOfDeletedEvents(3L)
                .build());
    }
}
