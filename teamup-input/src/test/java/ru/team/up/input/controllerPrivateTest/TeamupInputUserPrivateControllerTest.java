//package ru.team.up.input.controllerPrivateTest;
//
//import org.junit.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.team.up.core.entity.Interests;
//import ru.team.up.core.entity.User;
//import ru.team.up.input.controller.privateController.UserController;
//import ru.team.up.input.service.UserServiceRest;
//
//import javax.persistence.PersistenceException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Collections;
//
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
//public class TeamupInputUserPrivateControllerTest {
//
//    @Mock
//    private UserServiceRest userService;
//
////    @Autowired
//    @InjectMocks
//    private UserController userController;
//
//    @Before
//    public void setUp() {
////        MockitoAnnotations.initMocks(this);
////        метод initMocks помечен как устаревший, удалите эту строку вообще и над классом добавьте аннотацию -
////        @RunWith(MockitoJUnitRunner.class) из пакета import org.mockito.junit.MockitoJUnitRunner
//    }
//
//    Interests programming = Interests.builder()
//            .title("Programming")
//            .shortDescription("Like to write programs in java")
//            .build();
//
//    User testUser = User.builder()
//            .firstName("Aleksey")
//            .lastName("Tkachenko")
//            .middleName("Petrovich")
//            .username("alextk")
//            .email("alextk@bk.ru")
//            .password("1234")
//            .accountCreatedTime(LocalDate.now())
//            .lastAccountActivity(LocalDateTime.now())
//            .city("Moscow")
//            .birthday(LocalDate.of (1979, 1, 20))
//            .aboutUser("Studying to be a programmer.")
//            .userInterests(Collections.singleton(programming))
//            .build();
//
//    User emptyUser = User.builder()
//            .build();
//
//    ArrayList<User> listUser = new ArrayList<>();
//
//    @Ignore
//    @Test
//    public void testCreateUser() {
//        when(userService.saveUser(testUser)).thenReturn(testUser);
//        Assert.assertEquals(201, userController.createUser(testUser).getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testCreateUserException() {
//        when(userService.saveUser(emptyUser)).thenThrow(new PersistenceException());
//        Assert.assertEquals(400, userController.createUser(emptyUser).getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testGetOneById() {
//        when(userService.getUserById(testUser.getId())).thenReturn(testUser);
//        Assert.assertEquals(200, userController.getUserById(testUser.getId()).getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testGetOneByIdException() {
//        when(userService.getUserById(emptyUser.getId())).thenThrow(new PersistenceException());
//        Assert.assertEquals(400, userController.getUserById(emptyUser.getId()).getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testGetAllUser() {
//        listUser.add(testUser);
//        when(userService.getAllUsers()).thenReturn(listUser);
//        Assert.assertEquals(200, userController.getAllUsers().getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testGetAllUserException() {
//        listUser.add(emptyUser);
//        when(userService.getAllUsers()).thenThrow(new PersistenceException());
//        Assert.assertEquals(400, userController.getAllUsers().getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testUpdateUser() {
//        when(userService.saveUser(testUser)).thenReturn(testUser);
//        Assert.assertEquals(200, userController.updateUser(testUser.getId(), testUser).getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testUpdateUserException() {
//        when(userService.saveUser(emptyUser)).thenThrow(new PersistenceException());
//        Assert.assertEquals(400, userController.updateUser(emptyUser.getId(), emptyUser).getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testDeleteUser() {
//        Assert.assertEquals(202, userController.deleteUser(testUser.getId()).getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testDeleteUserException() {
//        doThrow(new PersistenceException()).when(userService).deleteUserById(emptyUser.getId());
//        Assert.assertEquals(400, userController.deleteUser(emptyUser.getId()).getStatusCodeValue());
//    }
//}
