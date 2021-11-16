package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
}
