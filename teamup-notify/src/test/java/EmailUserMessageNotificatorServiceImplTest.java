import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import ru.EmailUserMessageNotificatorService;
import ru.EmailUserMessageNotificatorServiceImpl;
import ru.team.up.core.entity.Status;
import ru.team.up.core.entity.User;
import ru.team.up.core.entity.UserMessage;
import ru.team.up.core.entity.UserMessageType;
import ru.team.up.core.repositories.UserMessageRepository;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
class EmailUserMessageNotificatorServiceImplTest {

    @Mock
    private UserMessageRepository userMessageRepository;

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailUserMessageNotificatorService emailUserMessageNotificatorService =
            new EmailUserMessageNotificatorServiceImpl(userMessageRepository, emailSender);

    private UserMessage userMessage;

    @BeforeEach
    private void setUpEntity() {
        MockitoAnnotations.openMocks(this);

        userMessage = UserMessage.builder()
                .message("Hi")
                .status(new Status())
                .messageCreationTime(LocalDateTime.now())
                .messageType(UserMessageType.NOT_SENT)
                .messageOwner(new User())
                .build();
    }

    @Test
    void send() {
        List<UserMessage> userMessageList = new LinkedList<>();
        userMessageList.add(userMessage);
        when(userMessageRepository.findAllByMessageType(UserMessageType.NOT_SENT)).thenReturn(userMessageList);
        emailUserMessageNotificatorService.send();
        assertEquals(UserMessageType.SENT, userMessage.getMessageType());
    }
}