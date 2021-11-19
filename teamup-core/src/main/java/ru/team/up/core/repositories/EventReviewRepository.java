package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.team.up.core.entity.EventReview;

@Repository
public interface EventReviewRepository extends JpaRepository<EventReview, Long> {
}
