package ru.team.up.input.controllerPublicTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import ru.team.up.input.payload.request.EventRequest;
import ru.team.up.input.payload.request.JoinRequest;
import ru.team.up.input.payload.request.UserRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamupInputEventPublicControllerTest {
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
            .accountCreatedTime("07.11.2021 16:55")
            .lastAccountActivity("07.11.2021 18:32")
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
            .accountCreatedTime("07.11.2021 16:55")
            .lastAccountActivity("07.11.2021 18:32")
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
            .timeEvent(LocalDateTime.now().plusDays(10))
            .eventUpdateDate(LocalDate.now())
            .participantsEvent(Collections.singletonList(testUser2))
            .eventType(eventType)
            .authorId(testUser)
            .eventInterests(Collections.singleton(programming))
            .status(status)
            .build();

    EventRequest eventRequest2 = EventRequest.builder()
            .event(event2)
            .build();

    JoinRequest joinRequest = JoinRequest.builder()
            .eventId(1L)
            .userId(1L)
            .build();

    @Test
    public void testCreate() throws Exception {
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        statusRepository.save(status);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/public/event?event=")
                        .content(objectToJsonString(eventRequest2))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/public/event/")
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
    public void testGetById() throws Exception {
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        statusRepository.save(status);
        eventRepository.save(event);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/public/event/{id}", event.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.id")
                        .value(event.getId()));
    }

    @Test
    public void testGetByName() throws Exception {
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        statusRepository.save(status);
        eventRepository.save(event);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/public/event/eventName/{eventName}", event.getEventName())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .jsonPath("[0].eventName")
                        .value(event.getEventName()));
    }

    @Test
    public void testFindEventsByAuthor() throws Exception {
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        statusRepository.save(status);
        eventRepository.save(event);
        UserRequest userRequest = UserRequest.builder()
                        .user(testUser)
                        .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/public/event")
                        .content(objectToJsonString(userRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testFindEventsByType() throws Exception {
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        statusRepository.save(status);
        eventRepository.save(event);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/public/event/type")
                        .content(objectToJsonString(event.getEventType()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
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
        EventRequest eventRequest = EventRequest.builder()
                .event(event)
                .build();
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/public/event/1")
                        .content(objectToJsonString(eventRequest))
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
                        .delete("/api/public/event/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testAddEventParticipant() throws Exception {
        eventTypeRepository.save(eventType);
        interestsRepository.save(programming);
        userRepository.save(testUser);
        userRepository.save(testUser2);
        statusRepository.save(status);
        eventRepository.save(event);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/public/event/join")
                        .content(objectToJsonString(joinRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteEventParticipant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/public/event/unjoin")
                        .content(objectToJsonString(joinRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String objectToJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            String now = mapper.writeValueAsString(obj);

//            return new ObjectMapper().writeValueAsString(obj);
            return now;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
