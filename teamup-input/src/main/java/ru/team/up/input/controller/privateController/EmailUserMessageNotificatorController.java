package ru.team.up.input.controller.privateController;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.core.service.EmailUserMessageNotificatorService;

@Slf4j
@RestController
@RequestMapping("/private/email-notificator")
public class EmailUserMessageNotificatorController {

    private EmailUserMessageNotificatorService emailUserMessageNotificatorService;


    @Autowired
    public EmailUserMessageNotificatorController(EmailUserMessageNotificatorService emailUserMessageNotificatorService) {
        this.emailUserMessageNotificatorService = emailUserMessageNotificatorService;
    }

    @GetMapping("/send")
    public ResponseEntity<String> sendEmailUserMessage() {
        log.debug("Начинаю процедуру рассылки уведомлений о новых сообщениях пользователей по электронной почте.");
        emailUserMessageNotificatorService.send();
        log.debug("Рассылка уведомлений о новых сообщениях пользователей по электронной почте завершена.");
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }

}
