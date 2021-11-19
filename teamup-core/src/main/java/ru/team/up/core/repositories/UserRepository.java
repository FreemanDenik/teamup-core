package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email =: email")
    User getUserByEmail(@Param("email") String email);

    User findByLogin(String login);;

    Object findUserAndRolesByName(String s);
}
