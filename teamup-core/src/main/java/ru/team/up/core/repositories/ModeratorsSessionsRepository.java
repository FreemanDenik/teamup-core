package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.team.up.core.entity.ModeratorsSessions;

import static org.hibernate.hql.internal.antlr.HqlTokenTypes.WHERE;
import static org.springframework.http.HttpHeaders.FROM;

public interface ModeratorsSessionsRepository extends JpaRepository<ModeratorsSessions, Long> {

   public void removeModeratorSession(Long id);
}
