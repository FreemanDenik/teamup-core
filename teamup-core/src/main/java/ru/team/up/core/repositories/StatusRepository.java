package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
