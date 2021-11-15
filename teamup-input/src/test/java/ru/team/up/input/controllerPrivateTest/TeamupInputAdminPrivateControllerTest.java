package ru.team.up.input.controllerPrivateTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.team.up.core.entity.Admin;
import ru.team.up.core.repositories.AdminRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexey Tkachenko
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TeamupInputAdminPrivateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminRepository adminRepository;

    Admin admin = Admin.builder()
            .id(1L)
            .name("Natalya")
            .lastName("Tkachenko")
            .middleName("Mihaylovna")
            .login("natatk")
            .email("natalyatk@bk.ru")
            .password("12345")
            .accountCreatedTime(LocalDate.of(2021, 10, 15))
            .lastAccountActivity(LocalDateTime.now())
            .build();

    @Test
    public void testCreateUser() throws Exception {
        admin.setId(2L);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/private/account/admin?admin=")
                        .content(objectToJsonString(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id")
                        .value(2L));
    }

    @Test
    public void testGetOneUserById() throws Exception {
        adminRepository.save(admin);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/private/account/admin/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id")
                        .value(1));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/private/account/admin")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.[*]")
                        .exists())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.[*]")
                        .isNotEmpty());
    }

    @Test
    public void testUpdateUser() throws Exception {
        admin.setEmail("mila@gmail.com");
        admin.setName("Mila");
        admin.setLogin("mila");
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/private/account/admin")
                        .content(objectToJsonString(admin))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.name")
                        .value("Mila"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.email")
                        .value("mila@gmail.com"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.login")
                        .value("mila"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id")
                        .value(admin.getId()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/private/account/admin/{id}", admin.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    public static String objectToJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
