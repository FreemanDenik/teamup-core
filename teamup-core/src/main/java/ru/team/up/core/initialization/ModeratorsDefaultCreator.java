package ru.team.up.core.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.entity.Role;
import ru.team.up.core.repositories.AccountRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Transactional
@Profile("cdb")
public class ModeratorsDefaultCreator {

    private final AccountRepository accountRepository;

    @Autowired
    public ModeratorsDefaultCreator(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Bean("ModeratorsDefaultCreator")
    public void moderatorsDefaultCreator() {
        accountRepository.save(Moderator.builder()
                .firstName("Moderator")
                .lastName("DefaultModerator")
                .username("moderator")
                .password(BCrypt.hashpw("moderator", BCrypt.gensalt(10)))
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .email("moderator@gmail.com")
                .role(Role.ROLE_MODERATOR)
                .amountOfCheckedEvents(2L)
                .amountOfClosedRequests(2L)
                .amountOfDeletedEvents(3L)
                .build());
    }
}