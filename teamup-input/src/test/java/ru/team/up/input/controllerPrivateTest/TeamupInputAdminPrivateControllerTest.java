//package ru.team.up.input.controllerPrivateTest;
//
//import org.junit.*;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import ru.team.up.core.entity.Account;
//import ru.team.up.core.entity.Admin;
//import ru.team.up.core.service.AdminService;
//import ru.team.up.input.controller.privateController.AdminController;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.when;
//
//@SpringBootTest
//@ExtendWith(MockitoExtension.class)
//public class TeamupInputAdminPrivateControllerTest {
//
//    @Mock
//    private AdminService adminService;
//
//    @Autowired
//    @InjectMocks
//    AdminController adminController;
//
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    Account admin = Admin.builder()
//            .firstName("Natalya")
//            .lastName("Tkachenko")
//            .middleName("Mihaylovna")
//            .username("natatk")
//            .email("natalyatk@bk.ru")
//            .password("12345")
//            .accountCreatedTime(LocalDate.now())
//            .lastAccountActivity(LocalDateTime.now())
//            .build();
//
//    ArrayList<Account> listAdmin = new ArrayList<>();
//
//    @Test
//    public void testCreateAdmin() {
//        when(adminService.saveAdmin(admin)).thenReturn(admin);
//        Assert.assertEquals(201, adminController.createAdmin((Admin) admin).getStatusCodeValue());
//    }
//
//    @Test
//    public void testGetOneById() {
//        when(adminService.getOneAdmin(admin.getId())).thenReturn(admin);
//        Assert.assertEquals(200, adminController.getOneAdmin(admin.getId()).getStatusCodeValue());
//    }
//
//    @Test
//    public void testGetAllAdmins() throws Exception {
//        listAdmin.add(admin);
//        when(adminService.getAllAdmins()).thenReturn(listAdmin);
//        Assert.assertEquals(200, adminController.getAllAdmins().getStatusCodeValue());
//    }
//
//    @Ignore
//    @Test
//    public void testUpdateAdmin() {
//        when(adminService.saveAdmin(admin)).thenReturn(admin);
//        Assert.assertEquals(200, adminController.updateAdmin(admin.getId(), (Admin) admin).getStatusCodeValue());
//    }
//
//    @Test
//    public void testDeleteAdmin() {
//        Assert.assertEquals(200, adminController.deleteAdmin(admin.getId()).getStatusCodeValue());
//    }
//
//}
