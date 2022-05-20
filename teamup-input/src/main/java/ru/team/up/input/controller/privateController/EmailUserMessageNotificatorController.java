package ru.team.up.input.controller.privateController;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.monitoring.service.MonitorProducerServiceImpl;
import ru.team.up.core.service.EmailUserMessageNotificatorService;
import ru.team.up.dto.ControlDto;

import javax.security.sasl.SaslClient;
import java.util.HashMap;

/**
 * @author Stepan Glushchenko
 * @link localhost:8080/swagger-ui.html
 */
@Slf4j
@RestController
@RequestMapping("/private/email-notificator")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmailUserMessageNotificatorController {

    private EmailUserMessageNotificatorService emailUserMessageNotificatorService;
    private MonitorProducerService monitorProducerService;

    @GetMapping("/send")
    public ResponseEntity<String> sendEmailUserMessage() {
        log.debug("Начинаю процедуру рассылки уведомлений о новых сообщениях пользователей по электронной почте.");
        emailUserMessageNotificatorService.send();
        log.debug("Рассылка уведомлений о новых сообщениях пользователей по электронной почте завершена.");

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        new HashMap<>())
        );
        return new ResponseEntity<>(HttpStatus.OK);
    }
}