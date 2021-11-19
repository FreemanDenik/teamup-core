package ru.team.up.core.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.core.repositories.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * @author Alexey Tkachenko
 */

@SpringBootTest
public class UserMessageTest {

    @Autowired
    private UserMessageRepository userMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestsRepository interestsRepository;

    @Autowired
    private StatusRepository statusRepository;

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
            .accountCreatedTime(LocalDate.now())
            .lastAccountActivity(LocalDateTime.now())
            .city("Moscow")
            .age(43)
            .aboutUser("Studying to be a programmer.")
            .userInterests(Collections.singleton(programming))
            .build();

    Status status = Status.builder()
            .status("unread")
            .build();

    UserMessage userMessage = UserMessage.builder()
            .messageOwner(testUser)
            .message("Hello!")
            .status(status)
            .messageCreationTime(LocalDateTime.now())
            .messageReadTime(LocalDateTime.now())
            .build();

    @Test
    void testUserMessage() {
        interestsRepository.save(programming);
        userRepository.save(testUser);
        statusRepository.save(status);
        userMessageRepository.save(userMessage);

        UserMessage userMessage1 = userMessageRepository.findById(1L).orElse(new UserMessage());
        Assertions.assertEquals(userMessage1.getMessage(), "Hello!");
        Assertions.assertEquals(userMessage1.getMessageOwner().getName(), "Aleksey");
        Assertions.assertEquals(userMessage1.getStatus().getStatus(), "unread");
        Assertions.assertEquals(userMessage1.getId(), 1L);
        Assertions.assertEquals("Hello!",
                userMessageRepository.findAllByMessageOwner(testUser).getMessage());
    }
}
