package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Role;
import ru.team.up.core.entity.User;

import java.util.List;

@Repository
public interface UserRepository extends AccountRepository {

    User findUserById(Long id);

    User findByEmail(String email);


    User findByUsername(String username);

    List<User> findAllUsersByRole(Role role);

    List<User> findUsersByCity(String city);
}
