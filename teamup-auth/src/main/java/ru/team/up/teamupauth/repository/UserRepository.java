package ru.team.up.teamupauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.team.up.teamupauth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String userName);
    @Query("select distinct u from User u join fetch u.roles where u.username = :login")
    User findUserAndRolesByName(@Param("login") String name);
}
