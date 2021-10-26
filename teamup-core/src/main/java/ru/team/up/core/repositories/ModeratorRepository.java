package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.Moderator;

public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
}
