package ru.team.up.input.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Role;
import ru.team.up.core.repositories.AccountRepository;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.UserServiceRest;

import java.util.List;

/**
 * Сервис для работы с пользователями
 *
 * @author Pavel Kondrashov
 */

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceRestImpl implements UserServiceRest {

    private final AccountRepository accountRepository;

    @Override
    @Transactional(readOnly = true)
    public Account getUserById(Long id) {
        return accountRepository.getById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getUserByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Account getUserByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public List<Account> getAllUsers() {
        return accountRepository.findAllByRole(Role.ROLE_USER);
    }

    @Override
    @Transactional
    public Account updateUser(UserRequest user, Long id) {
        Account oldUser = getUserById(id);
        user.getUser().setId(oldUser.getId());
        accountRepository.saveAndFlush(user.getUser());
        return getUserById(id);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        accountRepository.deleteById(id);
    }
}
