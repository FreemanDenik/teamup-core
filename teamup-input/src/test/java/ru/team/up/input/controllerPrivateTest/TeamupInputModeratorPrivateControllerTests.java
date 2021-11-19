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
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.repositories.ModeratorRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexey Tkachenko
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TeamupInputModeratorPrivateControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModeratorRepository moderatorRepository;

    Moderator moderator = Moderator.builder()
            .id(1L)
            .name("Moderator")
            .lastName("ModeratorLastName")
            .middleName("ModeratorMiddleName")
            .login("ModeratorLogin")
            .email("moderator@mail.ru")
            .password("1234")
            .accountCreatedTime("07.11.2021 19:00")
            .lastAccountActivity("07.11.2021 20:00")
            .amountOfCheckedEvents(10L)
            .amountOfDeletedEvents(11L)
            .amountOfClosedRequests(12L)
            .build();

    @Test
    public void testCreateUser() throws Exception {
        moderator.setId(2L);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/private/account/moderator?moderator=")
                        .content(objectToJsonString(moderator))
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
        moderatorRepository.save(moderator);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/private/account/moderator/{id}", 1)
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
                        .get("/private/account/moderator")
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
        moderator.setEmail("mila@gmail.com");
        moderator.setName("Mila");
        moderator.setLogin("mila");
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/private/account/moderator")
                        .content(objectToJsonString(moderator))
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
                        .value(moderator.getId()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/private/account/moderator/{id}", moderator.getId())
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
