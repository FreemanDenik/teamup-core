package ru.team.up.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.team.up.core.entity.User;
import ru.team.up.core.entity.UserMessage;

/**
 * @author Alexey Tkachenko
 */
public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
    UserMessage findAllByMessageOwner(User user);
}
