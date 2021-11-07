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
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.*;

import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexey Tkachenko
 */

@SpringBootTest
@AutoConfigureMockMvc
public class TeamupCoreEventControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestsRepository interestsRepository;

    @Autowired
    private StatusRepository statusRepository;

    EventType eventType = EventType.builder()
            .id(1L)
            .type("type")
            .build();

    Interests programming = Interests.builder()
            .id(1L)
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
            .accountCreatedTime("07.11.2021 16:55")
            .lastAccountActivity("07.11.2021 18:32")
            .city("Moscow")
            .age(43)
            .aboutUser("Studying to be a programmer.")
            .userInterests(Collections.singleton(programming))
            .build();

    Status status = Status.builder()
            .id(1L)
            .status("status")
            .build();

    Event event = Event.builder()
            .id(1L)
            .descriptionEvent("descriptionEvent")
            .placeEvent("placeEvent")
            .timeEvent("timeEvent")
            .participantsEvent("participantsEvent")
            .eventType(eventType)
            .authorId(testUser)
            .eventInterests(Collections.singleton(programming))
            .status(status)
            .build();

    @Test
    public void testCreateUser() throws Exception {
        event.setId(2L);
        testUser.setId(2L);
        event.setAuthorId(testUser);
        eventType.setId(2L);
        event.setEventType(eventType);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/private/event")
                        .content(objectToJsonString(event))
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
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        statusRepository.save(status);
        eventRepository.save(event);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/private/event/{id}", 1)
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
                        .get("/private/event")
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
        event.setDescriptionEvent("qwerty");
        event.setTimeEvent("123456");
        event.setParticipantsEvent("asdfgh");
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/private/event")
                        .content(objectToJsonString(event))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.descriptionEvent")
                        .value("qwerty"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.timeEvent")
                        .value("123456"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.participantsEvent")
                        .value("asdfgh"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id")
                        .value(event.getId()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/private/event/{id}", event.getId())
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
