package ru.team.up.input.controllerPrivateTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.service.AdminService;
import ru.team.up.input.controlle.privateController.AdminController;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TeamupInputAdminPrivateControllerTest {

    @Mock
    private AdminService adminService;

    @Autowired
    @InjectMocks
    AdminController adminController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    Admin admin = Admin.builder()
            .id(1L)
            .name("Natalya")
            .lastName("Tkachenko")
            .middleName("Mihaylovna")
            .login("natatk")
            .email("natalyatk@bk.ru")
            .password("12345")
            .accountCreatedTime("07.11.2021 18:00")
            .lastAccountActivity("07.11.2021 19:45")
            .build();

    ArrayList<Admin> listAdmin = new ArrayList<>();

    @Test
    public void testCreateAdmin() {
        when(adminService.saveAdmin(admin)).thenReturn(admin);
        Assert.assertEquals(201, adminController.createAdmin("admin", admin).getStatusCodeValue());
    }

    @Test
    public void testGetOneById() {
        when(adminService.getOneAdmin(admin.getId())).thenReturn(admin);
        Assert.assertEquals(200, adminController.getOneAdmin(admin.getId()).getStatusCodeValue());
    }

    @Test
    public void testGetAllAdmins() throws Exception {
        listAdmin.add(admin);
        when(adminService.getAllAdmins()).thenReturn(listAdmin);
        Assert.assertEquals(200, adminController.getAllAdmins().getStatusCodeValue());
    }

    @Test
    public void testUpdateAdmin() {
        when(adminService.saveAdmin(admin)).thenReturn(admin);
        Assert.assertEquals(200, adminController.updateAdmin(admin).getStatusCodeValue());
    }

    @Test
    public void testDeleteAdmin(){
        Assert.assertEquals(202, adminController.deleteAdmin(admin.getId()).getStatusCodeValue());
    }

}
