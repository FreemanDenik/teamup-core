package ru.team.up.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.AdminRepository;
import ru.team.up.core.repositories.EventRepository;
import ru.team.up.core.repositories.ModeratorRepository;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class DefoultEntitiesCreator {
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;
    private final EventRepository eventRepository;

    private final User user = User.builder()
            .id(1L)
            .name("user")
            .lastName("user")
            .middleName("user")
            .login("user")
            .password(BCrypt.hashpw("user", BCrypt.gensalt(10)))
            .accountCreatedTime(LocalDate.now())
            .lastAccountActivity(LocalDateTime.now())
            .email("user@mail.ru")
            .age(12)
            .role(Role.ROLE_USER)
            .build();

    private final Admin admin = Admin.builder()
            .id(2L)
            .name("admin")
            .lastName("admin")
            .middleName("admin")
            .login("admin")
            .password(BCrypt.hashpw("admin", BCrypt.gensalt(10)))
            .accountCreatedTime(LocalDate.now())
            .lastAccountActivity(LocalDateTime.now())
            .email("admin@mail.ru").role(Role.ROLE_ADMIN)
            .build();

    private final Moderator moderator = Moderator.builder()
            .id(3L)
            .name("moderator")
            .lastName("moderator")
            .middleName("moderator")
            .login("moderator")
            .password(BCrypt.hashpw("moderator", BCrypt.gensalt(10)))
            .accountCreatedTime(LocalDate.now())
            .lastAccountActivity(LocalDateTime.now())
            .email("moderator@mail.ru")
            .role(Role.ROLE_MODERATOR)
            .amountOfCheckedEvents(2L)
            .amountOfClosedRequests(2L)
            .amountOfDeletedEvents(3L)
            .build();
    private final Status status = Status.builder().status("Checked").id(1L).build();
    private final UserMessage userMessage = UserMessage.builder()
            .id(1L)
            .message("Bla-bla-bla")
            .messageOwner(user)
            .messageCreationTime(LocalDateTime.now())
            .status("Comment")
            .messageReadTime(LocalDateTime.now())
            .users(Collections.singleton(user))
            .build();
    private final EventType eventType = EventType.builder()
            .id(1L)
            .type("0oo00o0o")
            .build();

    private final Event event = Event.builder()
            .id(1L)
            .descriptionEvent("Going to the cinema")
            .placeEvent("123")
            .eventType(eventType)
            .participantsEvent(Collections.singletonList(user))
            .eventName("Cinema")
            .authorId(user)
            .timeEvent(LocalDateTime.now())
            .status(status)
            .build();

    private final Interests interests = Interests.builder()
            .id(1L)
            .event(Collections.singleton(event))
            .users(Collections.singleton(user))
            .shortDescription("aasdas")
            .title("QWE")
            .build();

    @Autowired
    public DefoultEntitiesCreator(UserRepository userRepository, AdminRepository adminRepository, ModeratorRepository moderatorRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
        this.moderatorRepository = moderatorRepository;
        this.eventRepository = eventRepository;
    }

    @Bean
    public void accountsCreator() {
        userRepository.save(user);
        adminRepository.save(admin);
        moderatorRepository.save(moderator);
    }

    @Bean
    public void eventCreator() {
        eventRepository.save(event);
    }
}
