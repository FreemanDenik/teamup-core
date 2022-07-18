package ru.team.up.core.service;

import ru.team.up.core.entity.Account;

import java.util.Optional;

public interface AccountService {

    Optional<Account> findByUserName (String userName);

    Optional<Account> getUserByEmail (String userName);


}
