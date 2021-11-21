package ru.team.up.input.controllerPrivateTest;

import org.junit.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.entity.Interests;
import ru.team.up.core.entity.User;
import ru.team.up.core.service.UserService;
import ru.team.up.input.controller.privateController.UserController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TeamupInputUserPrivateControllerTest {

    @Mock
    private UserService userService;

    @Autowired
    @InjectMocks
    private UserController userController;

    Interests programming = Interests.builder()
            .title("Programming")
            .shortDescription("Like to write programs in java")
            .build();

    User testUser = User.builder()
            .id(1L)
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

    ArrayList<User> listUser = new ArrayList<>();

    @Test
    public void testCreateUser() {
        when(userService.saveUser(testUser)).thenReturn(testUser);
        Assert.assertEquals(201, userController.createUser("testUser", testUser).getStatusCodeValue());
    }

    @Test
    public void testGetOneById() {
        when(userService.getOneUser(testUser.getId())).thenReturn(testUser);
        Assert.assertEquals(200, userController.getOneUser(testUser.getId()).getStatusCodeValue());
    }

    @Test
    public void testGetAllUser() {
        listUser.add(testUser);
        when(userService.getAllUsers()).thenReturn(listUser);
        Assert.assertEquals(200, userController.getAllUsers().getStatusCodeValue());
    }

    @Test
    public void testUpdateUser() {
        when(userService.saveUser(testUser)).thenReturn(testUser);
        Assert.assertEquals(200, userController.updateUser(testUser).getStatusCodeValue());
    }

    @Test
    public void testDeleteUser() {
        Assert.assertEquals(202, userController.deleteUser(testUser.getId()).getStatusCodeValue());
    }
}
