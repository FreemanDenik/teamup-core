package ru.team.up.input.controller.privateController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Application;
import ru.team.up.core.entity.User;
import ru.team.up.core.service.ApplicationService;
import ru.team.up.sup.service.ParameterService;
import ru.team.up.core.service.EventService;
import ru.team.up.core.service.UserService;
import ru.team.up.dto.ControlDto;
import ru.team.up.input.payload.request.RequestWrapper;

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
            log.debug("Метод getAllApplicationsByEventId выключен параметром getAllApplicationsByEventId = false");
            throw new RuntimeException("Method getAllApplicationsByEventId is disabled by parameter getAllApplicationsByEventId");
        }
        log.debug("Получен запрос на поиск заявок с id мероприятия: {}", id);

        Event event = eventService.getOneEvent(id);

        List<Application> applications = applicationService.getAllApplicationsByEventId(id);

        Map<String, Object> monitoringParameters = new LinkedHashMap<>();
        monitoringParameters.put("Id мероприятия: ", event.getId());
        monitoringParameters.put("Название мероприятия: ", event.getEventName());
        monitoringParameters.put("Количество заявок у мероприятия ", applications.size());

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
        Map<String, Object> monitoringParameters = new LinkedHashMap<>();

        monitoringParameters.put("Id пользователя ", user.getId());
        monitoringParameters.put("Имя пользователя ", user.getUsername());
        monitoringParameters.put("Количество заявок у пользователя ", applications.size());

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return ResponseEntity.ok(applications);
    }

    @PostMapping
    public ResponseEntity<Application> sendApplication(@RequestParam User user, @RequestBody @NotNull Application applicationCreate) {
        if (!ParameterService.sendApplicationEnabled.getValue()) {
            log.debug("Метод sendApplication выключен параметром sendApplicationEnabled = false");
            throw new RuntimeException("Method sendApplication is disabled by parameter sendApplicationEnabled");
        }
    public ResponseEntity<Application> sendApplication(@RequestBody RequestWrapper requestWrapper) {
        Application applicationCreate = requestWrapper.getApplication();
        User user = requestWrapper.getUser();

        log.debug("Запрос на отправку зявки {}", applicationCreate);

        ResponseEntity<Application> responseEntity = new ResponseEntity<>(applicationService.saveApplication(applicationCreate,user), HttpStatus.CREATED);

        Map<String, Object> monitoringParameters = new LinkedHashMap<>();
        monitoringParameters.put("Id пользователя:", user.getId());
        monitoringParameters.put("Email пользователя:", user.getEmail());
        monitoringParameters.put("Id заявки:", applicationCreate.getId());

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), monitoringParameters));

        return responseEntity;
    }
}
