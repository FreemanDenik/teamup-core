package ru.team.up.core.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.team.up.core.entity.*;
import ru.team.up.core.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class SendMessageServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SendMessageService sendMessageService = new SendMessageServiceImpl(userRepository);

    private UserMessage userMessageTest;
    private User userTest;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        userMessageTest = UserMessage.builder()
                .id(1L)
                .message("Hi")
                .status(new Status())
                .messageCreationTime(LocalDateTime.now())
                .messageType(UserMessageType.NOT_SENT)
                .messageOwner(new User())
                .build();

        userTest = User.builder()
                .id(1L)
                .email("userka@mail.ru")
                .username("NeoTheUser")
                .firstName("Neo")
                .lastName("Neon")
                .password("Neo12345")
                .accountCreatedTime(LocalDate.now())
                .lastAccountActivity(LocalDateTime.now())
                .role(Role.ROLE_USER)
                .userMessages(new HashSet<>())
                .build();
    }

    @Test
    void sendMessage() {
        when(userRepository.save(userTest)).thenReturn(userTest);
        // Пробуем сохранить сообщение
        sendMessageService.sendMessage(userTest, userMessageTest);
        // Проверяем был ли вызван метод save() у репозитория
        verify(userRepository, atLeastOnce()).save(any(User.class));
    }
}