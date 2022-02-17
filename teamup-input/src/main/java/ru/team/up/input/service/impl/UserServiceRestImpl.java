package ru.team.up.input.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.User;
import ru.team.up.core.exception.UserNotFoundEmailException;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.exception.UserNotFoundUsernameException;
import ru.team.up.core.service.UserService;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.UserServiceRest;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с пользователями
 *
 * @author Pavel Kondrashov
 */

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceRestImpl implements UserServiceRest {

    private UserService userService;

    @Override
    public User getUserById(Long id) {
        Optional<Account> user = userService.getOneUser(id);
        return (User) userService.getOneUser(id).orElseThrow(() -> new UserNotFoundIDException(id));
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) userService.findByEmail(email).orElseThrow(() -> new UserNotFoundEmailException(email));
    }

    @Override
    public User getUserByUsername(String username) {
        return (User) userService.findByUsername(username).orElseThrow(() -> new UserNotFoundUsernameException(username));
    }

    @Override
    public List<User> getAllUsers() {
        List<? extends Account> users = userService.getAllUsers();

        return (List<User>) users;
    }

    @Override
    public User saveUser(User user) {
        return (User) userService.saveUser(user);
    }

    @Override
    public User updateUser(UserRequest user, Long id) {
        User oldUser = getUserById(id);
        User newUser = user.getUser();
        newUser.setId(oldUser.getId());
        return (User) userService.saveUser(newUser);
    }

    @Override
    public void deleteUserById(Long id) {
        userService.deleteUser(id);
    }

    @Override
    public List<User> getTopUsersInCity(String city) {
        List<? extends Account> users = userService.getTopUsersInCity(city);
        return (List<User>) users;
    }
}
