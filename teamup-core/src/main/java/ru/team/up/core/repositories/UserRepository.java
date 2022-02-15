package ru.team.up.core.repositories;

import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;

@Repository
public interface UserRepository extends AccountRepository {

    User getUserById(Long id);

    User findByUsername(String login);

    Account findByEmail(String email);
}
