package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Role;
import ru.team.up.core.repositories.AccountRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Transactional
@Profile("cdb")
public class AdminsDefaultCreator {

    private final AccountRepository accountRepository;

    @Autowired
    public AdminsDefaultCreator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Bean("AdminsDefaultCreator")
    public void adminsDefaultCreator() {
        accountRepository.save(Admin.builder()
                .firstName("Admin")
                .lastName("DefaultAdmin")
                .username("admin")
                .password(BCrypt.hashpw("admin", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("admin@gmail.com")
                .role(Role.ROLE_ADMIN)
                .build());
    }
}