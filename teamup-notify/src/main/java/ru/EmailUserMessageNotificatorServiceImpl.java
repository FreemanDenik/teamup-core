package ru;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.team.up.core.entity.UserMessage;
import ru.team.up.core.entity.UserMessageType;
import ru.team.up.core.repositories.UserMessageRepository;

/**
 * @author Stepan Glushchenko
 * Почтовый сервис, выполняющий отправку уведомления по электронной почте о новом сообщении пользователя
 */
@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmailUserMessageNotificatorServiceImpl implements EmailUserMessageNotificatorService {

    private UserMessageRepository userMessageRepository;
    private JavaMailSender emailSender;

    /**
     * Метод отправляет уведомления по электронной почте о новом сообщении пользователя.
     */
    @Override
    public void send() {
        for (UserMessage userMessage : userMessageRepository.findAllByMessageType(UserMessageType.NOT_SENT)) {
            log.debug("Подготовка сообщения id:{} к отправке для пользователя {}",
                    userMessage.getId(), userMessage.getMessageOwner().getUsername());
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(userMessage.getMessageOwner().getEmail());
            message.setSubject("Новое сообщение");
            message.setText(userMessage.toString());
            try {
                emailSender.send(message);
            } catch (MailException e) {
                log.debug("Ошибка при отправке уведомлений по электронной почте. Сообщение id:{}, пользователь:{}. {}",
                        userMessage.getId(), userMessage.getMessageOwner().getUsername(), e.toString());
                continue;
            }
            userMessage.setMessageType(UserMessageType.SENT);
            userMessageRepository.save(userMessage);
            log.debug("Уведомление по электронной почте о сообщении id:{} для пользователя {} отправлены.",
                    userMessage.getId(), userMessage.getMessageOwner().getUsername());
        }
    }
}