package ru.team.up.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.team.up.core.entity.Interests;
import ru.team.up.core.entity.User;
import ru.team.up.core.repositories.InterestsRepository;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TeamupCoreUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestsRepository interestsRepository;

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

    @Test
    public void testCreateUser() throws Exception {
        testUser.setId(2L);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/private/account/user?user=")
                        .content(objectToJsonString(testUser))
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
        interestsRepository.save(programming);
        userRepository.save(testUser);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/private/account/user/{id}", 1)
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
                        .get("/private/account/user")
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
        testUser.setAge(24);
        testUser.setName("Ruslan");
        testUser.setCity("Lyubertsy");
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/private/account/user")
                        .content(objectToJsonString(testUser))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.name")
                        .value("Ruslan"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.city")
                        .value("Lyubertsy"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.age")
                        .value("24"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id")
                        .value(testUser.getId()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/private/account/user/{id}", testUser.getId())
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
