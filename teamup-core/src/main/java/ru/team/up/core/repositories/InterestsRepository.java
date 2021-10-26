package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.Interests;

public interface InterestsRepository extends JpaRepository<Interests, Long> {
}
