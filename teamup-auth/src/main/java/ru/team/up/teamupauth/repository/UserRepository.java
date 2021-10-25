package ru.team.up.teamupauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.team.up.teamupauth.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserByUsername(String userName);
}
