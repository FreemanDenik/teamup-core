package ru.team.up.core.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.Account;
import ru.team.up.core.repositories.AccountRepository;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImp implements AccountService {

    private AccountRepository accountRepository;

    public Optional<Account> findByUserName (String userName){
        return Optional.ofNullable(accountRepository.findByUserName(userName));
    }

    @Override
    public Optional<Account> getUserByEmail(String email) {
        return Optional.ofNullable(accountRepository.findByEmail(email));
    }
}
