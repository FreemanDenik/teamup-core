package ru.team.up.input.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;
import ru.team.up.core.exception.UserNotFoundEmailException;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.exception.UserNotFoundUsernameException;
import ru.team.up.core.service.EventService;
import ru.team.up.core.service.UserService;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.UserServiceRest;

import java.util.List;

/**
 * Сервис для работы с пользователями
 *
 * @author Pavel Kondrashov
 */

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceRestImpl implements UserServiceRest {

    private UserService userService;
    private EventService eventService;

    @Override
    public User getUserById(Long id) {

        return userService.getOneUser(id).orElseThrow(() -> new UserNotFoundIDException(id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userService.findByEmail(email).orElseThrow(() -> new UserNotFoundEmailException(email));
    }

    @Override
    public User getUserByUsername(String username) {
        return userService.findByUsername(username).orElseThrow(() -> new UserNotFoundUsernameException(username));
    }

    @Override
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public List<Event> getEventsByOwnerId(Long id) {
        return eventService.getAllByAuthorId(id);
    }

    @Override
    public List<Event> getEventsBySubscriberId(Long id) {
        return eventService.getAllEventsBySubscriberId(id);
    }

    @Override
    public User updateUserError(User user, Long id) {
        User oldUser = getUserById(id);
        User newUser = user;
        newUser.setId(oldUser.getId());
        return userService.saveUser(newUser);
    }

    @Override
    public User saveUser(User user) {
        return userService.saveUser(user);
    }

    @Override
    public User updateUser(UserRequest userRequest, Long id) {
        User oldUser = getUserById(id);
        User newUser = userRequest.getUser();
        newUser.setId(oldUser.getId());
        return userService.saveUser(newUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userService.deleteUser(id);
    }

    @Override
    public List<User> getTopUsersInCity(String city) {
        return userService.getTopUsersInCity(city);
    }

    @Override
    public List<User> getAllUsersByCityByLimit(String city, int limit) {
        return userService.getAllUsersByCityByLimit(city, limit);
    }
}
