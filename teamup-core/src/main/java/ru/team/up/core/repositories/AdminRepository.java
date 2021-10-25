package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
}
