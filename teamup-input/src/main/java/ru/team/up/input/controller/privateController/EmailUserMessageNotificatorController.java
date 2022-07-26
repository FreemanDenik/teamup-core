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
import ru.EmailUserMessageNotificatorService;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.dto.ControlDto;

import java.util.HashMap;
import ru.team.up.sup.service.ParameterService;


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
        if (!ParameterService.sendEmailUserMessageEnabled.getValue()) {
            log.debug("Метод sendEmailUserMessage выключен параметром sendEmailUserMessageEnabled = false");
            throw new RuntimeException("Method sendEmailUserMessage is disabled by parameter sendEmailUserMessageEnabled");
        }
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