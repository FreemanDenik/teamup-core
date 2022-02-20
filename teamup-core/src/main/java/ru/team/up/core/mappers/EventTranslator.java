package ru.team.up.core.mappers;


import org.springframework.beans.factory.annotation.Autowired;
import ru.team.up.core.entity.User;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.service.UserServiceImpl;


public class EventTranslator {

    @Autowired
    private UserServiceImpl userService;


    public long UserToLongEventAuthorIdDto (User user) {
        return user.getId();
    }

    public User EventDtoAuthorToEventAuthor(Long authorId) {

        return userService.getOneUser(authorId).orElseThrow(() -> new UserNotFoundIDException(authorId));
    }
}
