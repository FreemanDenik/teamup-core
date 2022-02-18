package ru.team.up.input.controllerPublicTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.entity.User;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.input.controller.publicController.UserRestControllerPublic;
import ru.team.up.input.payload.request.UserRequest;
import ru.team.up.input.service.UserServiceRest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TeamupInputUserPublicControllerTest {
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Mock
    private UserServiceRest userService;

    @InjectMocks
    private UserRestControllerPublic userRestControllerPublic;

    User testUser = User.builder ()
            .firstName("Marina")
            .lastName("Sysenko")
            .middleName("Alexsandrovna")
            .username("test")
            .email("testemail@gmail.com")
            .password("1234")
            .accountCreatedTime(LocalDate.of(2021,11,20))
            .lastAccountActivity(LocalDateTime.of(2021,11,20,19,00))
            .city("Volgograd")
            .birthday(LocalDate.of (1967, 1, 20))
            .aboutUser("I like to cook")
            .build();
    @Test
    public void testGetByIdUserFind() {
        when(userService.getUserById (1L)).thenReturn (testUser);
        Assert.assertEquals(200, userRestControllerPublic.getUserById (1L).getStatusCodeValue());
    }

    //TODO Сделать проверку, что когда юзер не найден, то возвращается код 204. Пока не смог это сделать из-за того, что у нас ошибка теперь вываливается на уровке сервис слоя
    @Test
    public void testGetByIdUserNotFind() {
        //Данный тест не работает из-за того, что у нас ошибка теперь вываливается на уровке сервис слоя
        when(userService.getUserById (1L)).thenReturn (testUser);
        Assert.assertThrows(UserNotFoundIDException.class, () -> userRestControllerPublic.getUserById (2L));
        //еще один вариант и тоже не рабочий
        //Assert.assertEquals (204,userRestControllerPublic.getUserById (2L).getStatusCodeValue ());
    }

    //TODO Сделать проверку, что в id передали некорректные данные, вместо Long передали например String. Пока не смог это сделать
    @Test
    public void testGetByIdUserBadRequest() {
        Assert.assertEquals (400,userRestControllerPublic.getUserById (2L).getStatusCodeValue ());
    }

    @Test
    public void testGetByEmail() {
        when(userService.getUserByEmail ("roshepkina34@gmail.com")).thenReturn(testUser);
        Assert.assertEquals(200, userRestControllerPublic.getUserByEmail ("roshepkina34@gmail.com").getStatusCodeValue());
    }
    @Test
    public void testGetByUsername() {
        when(userService.getUserByUsername ("test")).thenReturn(testUser);
        Assert.assertEquals(200, userRestControllerPublic.getUserByUsername ("test").getStatusCodeValue());
    }
    @Test
    public void getAllUsers(){
        when(userService.getAllUsers ()).thenReturn (Collections.singletonList (testUser));
        Assert.assertEquals (200,userRestControllerPublic.getUsersList ().getStatusCodeValue ());
    }
    @Test
    public void updateUser(){
        when(userService.getUserById(1L)).thenReturn (testUser);
        Assert.assertEquals(200, userRestControllerPublic.updateUser (new UserRequest ((User) testUser), 1L).getStatusCodeValue());
    }
    @Test
    public void testDeleteUser() {
        when (userService.getUserById (testUser.getId ())).thenReturn (testUser);
        Assert.assertEquals(200, userRestControllerPublic.deleteUserById (testUser.getId ()).getStatusCodeValue());
    }
    @Test
    public void getTopUsersListInCity(){
        when(userService.getTopUsersInCity ("Санкт-Петербург")).thenReturn (Collections.singletonList (testUser));
        Assert.assertEquals (200,userRestControllerPublic.getTopUsersListInCity("Санкт-Петербург").getStatusCodeValue ());
    }
}

