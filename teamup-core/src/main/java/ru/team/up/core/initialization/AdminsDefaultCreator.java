package ru.team.up.core.initialization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.entity.Role;
import ru.team.up.core.repositories.AdminRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Component
@Transactional
public class AdminsDefaultCreator {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminsDefaultCreator(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Bean("AdminsDefaultCreator")
    public void adminsDefaultCreator() {
        adminRepository.save(Admin.builder()
                .id(1L)
                .name("Admin")
                .lastName("DefaultAdmin")
                .login("admin")
                .password(BCrypt.hashpw("admin", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("admin@gmail.com")
                .role(Role.ROLE_ADMIN)
                .build());
    }
}