package ru.team.up.input.controller.privateController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Application;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;
import ru.team.up.core.service.ApplicationService;
import ru.team.up.dto.ParametersDto;
import ru.team.up.sup.service.ParameterService;
import ru.team.up.core.service.EventService;
import ru.team.up.core.service.UserService;
import ru.team.up.dto.ControlDto;
import ru.team.up.input.payload.request.RequestWrapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("private/application")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationController {

    private ApplicationService applicationService;
    private MonitorProducerService monitoringProducerService;
    private EventService eventService;
    private UserService userService;

    @GetMapping("/ByEvent/{id}")
    public ResponseEntity<List<Application>> getAllApplicationsByEventId(@PathVariable Long id) {
        if (!ParameterService.getAllApplicationsByEventIdEnabled.getValue()) {
            log.debug("Метод getAllApplicationsByEventId выключен параметром getAllApplicationsByEventIdEnabled = false");
            throw new RuntimeException("Method getAllApplicationsByEventId is disabled by parameter getAllApplicationsByEventIdEnabled");
        }
        log.debug("Получен запрос на поиск заявок с id мероприятия: {}", id);

        Event event = eventService.getOneEvent(id);

        List<Application> applications = applicationService.getAllApplicationsByEventId(id);

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto eventId = ParametersDto.builder()
                .description("Id мероприятия: ")
                .value(event.getId())
                .build();

        ParametersDto eventName = ParametersDto.builder()
                .description("Название мероприятия: ")
                .value(event.getEventName())
                .build();

        ParametersDto applicationSize = ParametersDto.builder()
                .description("Количество заявок у мероприятия ")
                .value(applications.size())
                .build();


        monitoringParameters.put("Id мероприятия: ", eventId);
        monitoringParameters.put("Название мероприятия: ", eventName);
        monitoringParameters.put("Количество заявок у мероприятия ", applicationSize);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return ResponseEntity.ok(applications);
    }

    @GetMapping("/ByUser/{id}")
    public ResponseEntity<List<Application>> getAllApplicationsByUserId(@PathVariable Long id) {
        if (!ParameterService.getAllApplicationsByUserIdEnabled.getValue()) {
            log.debug("Метод getAllApplicationsByUserId выключен параметром getAllApplicationsByUserIdEnabled = false");
            throw new RuntimeException("Method getAllApplicationsByUserId is disabled by parameter getAllApplicationsByUserIdEnabled");
        }
        log.debug("Получен запрос на поиск заявок с id пользователя: {}", id);

        User user = userService.getOneUser(id).orElse(null);
        if (user == null) {
            log.error("Пользвателя с id {} не существует", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<Application> applications = applicationService.getAllApplicationsByUserId(id);
        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto userId = ParametersDto.builder()
                .description("Id пользователя ")
                .value(user.getId())
                .build();

        ParametersDto userName = ParametersDto.builder()
                .description("Имя пользователя ")
                .value(user.getUsername())
                .build();

        ParametersDto userApplicationSize = ParametersDto.builder()
                .description("Количество заявок у пользователя ")
                .value(applications.size())
                .build();

        monitoringParameters.put("Id пользователя ", userId);
        monitoringParameters.put("Имя пользователя ", userName);
        monitoringParameters.put("Количество заявок у пользователя ", userApplicationSize);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return ResponseEntity.ok(applications);
    }

    @PostMapping
    public ResponseEntity<Application> sendApplication(@RequestBody RequestWrapper requestWrapper) {
        if (!ParameterService.sendApplicationEnabled.getValue()) {
            log.debug("Метод sendApplication выключен параметром sendApplicationEnabled = false");
            throw new RuntimeException("Method sendApplication is disabled by parameter sendApplicationEnabled");
        }
        Application applicationCreate = requestWrapper.getApplication();
        User user = requestWrapper.getUser();

        log.debug("Запрос на отправку зявки {}", applicationCreate);

        ResponseEntity<Application> responseEntity = new ResponseEntity<>(applicationService.saveApplication(applicationCreate, user), HttpStatus.CREATED);

        Map<String, ParametersDto> monitoringParameters = new LinkedHashMap<>();

        ParametersDto userId = ParametersDto.builder()
                .description("Id пользователя: ")
                .value(user.getId())
                .build();

        ParametersDto userEmail = ParametersDto.builder()
                .description("IEmail пользователя: ")
                .value(user.getEmail())
                .build();

        ParametersDto applicationId = ParametersDto.builder()
                .description("Id заявки: ")
                .value(applicationCreate.getId())
                .build();

        monitoringParameters.put("Id пользователя:",userId);
        monitoringParameters.put("Email пользователя:", userEmail);
        monitoringParameters.put("Id заявки:", applicationId);

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return responseEntity;
    }
}
