package ru.team.up.input.controllerPrivateTest;

import org.junit.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.entity.Interests;
import ru.team.up.core.entity.User;
import ru.team.up.core.service.UserService;
import ru.team.up.input.controller.privateController.UserController;

import javax.persistence.PersistenceException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class TeamupInputUserPrivateControllerTest {

    @Mock
    private UserService userService;

//    @Autowired
    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        метод initMocks помечен как устаревший, удалите эту строку вообще и над классом добавьте аннотацию -
//        @RunWith(MockitoJUnitRunner.class) из пакета import org.mockito.junit.MockitoJUnitRunner
    }

    Interests programming = Interests.builder()
            .title("Programming")
            .shortDescription("Like to write programs in java")
            .build();

    User testUser = User.builder()
            .id(98L)
            .name("Aleksey")
            .lastName("Tkachenko")
            .middleName("Petrovich")
            .login("alextk")
            .email("alextk@bk.ru")
            .password("1234")
            .accountCreatedTime(LocalDate.now())
            .lastAccountActivity(LocalDateTime.now())
            .city("Moscow")
            .age(43)
            .aboutUser("Studying to be a programmer.")
            .userInterests(Collections.singleton(programming))
            .build();

    User emptyUser = User.builder()
            .id(99L)
            .build();

    ArrayList<User> listUser = new ArrayList<>();

    @Test
    public void testCreateUser() {
        when(userService.saveUser(testUser)).thenReturn(testUser);
        Assert.assertEquals(201, userController.createUser("testUser", testUser).getStatusCodeValue());
    }

    @Test
    public void testCreateUserException() {
        when(userService.saveUser(emptyUser)).thenThrow(new PersistenceException());
        Assert.assertEquals(400, userController.createUser("emptyUser", emptyUser).getStatusCodeValue());
    }

    @Test
    public void testGetOneById() {
        when(userService.getOneUser(testUser.getId())).thenReturn(testUser);
        Assert.assertEquals(200, userController.getOneUser(testUser.getId()).getStatusCodeValue());
    }

    @Test
    public void testGetOneByIdException() {
        when(userService.getOneUser(emptyUser.getId())).thenThrow(new PersistenceException());
        Assert.assertEquals(400, userController.getOneUser(emptyUser.getId()).getStatusCodeValue());
    }

    @Test
    public void testGetAllUser() {
        listUser.add(testUser);
        when(userService.getAllUsers()).thenReturn(listUser);
        Assert.assertEquals(200, userController.getAllUsers().getStatusCodeValue());
    }

    @Test
    public void testGetAllUserException() {
        listUser.add(emptyUser);
        when(userService.getAllUsers()).thenThrow(new PersistenceException());
        Assert.assertEquals(400, userController.getAllUsers().getStatusCodeValue());
    }

    @Test
    public void testUpdateUser() {
        when(userService.saveUser(testUser)).thenReturn(testUser);
        Assert.assertEquals(200, userController.updateUser(testUser).getStatusCodeValue());
    }

    @Test
    public void testUpdateUserException() {
        when(userService.saveUser(emptyUser)).thenThrow(new PersistenceException());
        Assert.assertEquals(400, userController.updateUser(emptyUser).getStatusCodeValue());
    }

    @Test
    public void testDeleteUser() {
        Assert.assertEquals(202, userController.deleteUser(testUser.getId()).getStatusCodeValue());
    }

    @Test
    public void testDeleteUserException() {
        doThrow(new PersistenceException()).when(userService).deleteUser(emptyUser.getId());
        Assert.assertEquals(400, userController.deleteUser(emptyUser.getId()).getStatusCodeValue());
    }
}
