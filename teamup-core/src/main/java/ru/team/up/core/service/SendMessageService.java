package ru.team.up.core.service;


import ru.team.up.core.entity.User;
import ru.team.up.core.entity.UserMessage;

import java.util.List;
import java.util.Set;

/**
 * @author Stanislav Ivashchenko
 */
public interface SendMessageService {

    void sendMessage(User user, UserMessage message);

    void sendMessage(Set<User> users, UserMessage message);

    void sendMessage(List<User> users, UserMessage message);
}
