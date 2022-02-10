package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByEmail(String email);

    User getUserById(Long id);

    User findByUsername(String login);;

    Object findUserAndRolesByFirstName(String s);

    Account findByEmail(String email);
}
