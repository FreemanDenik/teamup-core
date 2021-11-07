package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.Moderator;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, Long> {
}
