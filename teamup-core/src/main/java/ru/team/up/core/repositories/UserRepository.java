package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

}
