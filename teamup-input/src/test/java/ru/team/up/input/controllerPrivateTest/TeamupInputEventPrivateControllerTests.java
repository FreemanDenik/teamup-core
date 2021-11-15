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
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexey Tkachenko
 */

@SpringBootTest
@AutoConfigureMockMvc
public class TeamupInputEventPrivateControllerTests {
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
            .type("type")
            .build();

    Interests programming = Interests.builder()
            .title("Programming")
            .shortDescription("Like to write programs in java")
            .build();

    User testUser = User.builder()
            .name("Aleksey")
            .lastName("Tkachenko")
            .middleName("Petrovich")
            .login("alextk")
            .email("alextk@bk.ru")
            .password("1234")
            .accountCreatedTime(LocalDate.of(2021, 10, 15))
            .lastAccountActivity(LocalDateTime.now())
            .city("Moscow")
            .age(43)
            .aboutUser("Studying to be a programmer.")
            .userInterests(Collections.singleton(programming))
            .build();

    User testUser2 = User.builder()
            .name("Aleksey2")
            .lastName("Tkachenko2")
            .middleName("Petrovich2")
            .login("alextk2")
            .email("alextk2@bk.ru")
            .password("1234")
            .accountCreatedTime(LocalDate.of(2021, 10, 15))
            .lastAccountActivity(LocalDateTime.now())
            .city("Moscow")
            .age(43)
            .aboutUser("Studying to be a programmer.")
            .userInterests(Collections.singleton(programming))
            .build();

    Status status = Status.builder()
            .status("status")
            .build();

    Event event = Event.builder()
            .eventName("eventName")
            .descriptionEvent("descriptionEvent")
            .placeEvent("placeEvent")
            .timeEvent(LocalDateTime.now())
            .eventUpdateDate(LocalDate.now())
            .participantsEvent(Collections.singletonList(testUser2))
            .eventType(eventType)
            .authorId(testUser)
            .eventInterests(Collections.singleton(programming))
            .status(status)
            .build();

    Event event2 = Event.builder()
            .eventName("eventName2")
            .descriptionEvent("descriptionEvent2")
            .placeEvent("placeEvent2")
            .timeEvent(LocalDateTime.now())
            .eventUpdateDate(LocalDate.now())
            .participantsEvent(Collections.singletonList(testUser2))
            .eventType(eventType)
            .authorId(testUser)
            .eventInterests(Collections.singleton(programming))
            .status(status)
            .build();

    @Test
    public void testCreate() throws Exception {
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        statusRepository.save(status);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/private/event?event=")
                        .content(objectToJsonString(event2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetOneById() throws Exception {
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        statusRepository.save(status);
        eventRepository.save(event);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/private/event/{id}", event.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id")
                        .value(event.getId()));
    }

    @Test
    public void testGetAll() throws Exception {
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
    public void testUpdate() throws Exception {
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        statusRepository.save(status);
        eventRepository.save(event);
        event.setDescriptionEvent("qwerty");
        event.setEventName("newname");
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
                        .jsonPath("$.eventName")
                        .value("newname"))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id")
                        .value(event.getId()));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/private/event/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted());
    }

    public static String objectToJsonString(final Object obj) {
        try {
            return new ObjectMapper().findAndRegisterModules().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
