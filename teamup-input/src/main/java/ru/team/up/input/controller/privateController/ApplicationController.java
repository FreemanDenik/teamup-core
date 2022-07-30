package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Application;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.User;
import ru.team.up.core.mappers.ApplicationMapper;
import ru.team.up.core.service.ApplicationService;
import ru.team.up.dto.ApplicationDto;
import ru.team.up.dto.ParametersDto;
import ru.team.up.sup.service.ParameterService;
import ru.team.up.core.service.EventService;
import ru.team.up.core.service.UserService;
import ru.team.up.dto.ControlDto;
import ru.team.up.input.payload.request.RequestWrapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("private/application")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Tag(name = "Application Private Controller", description = "Приватный адрес для работы с заявками")
public class ApplicationController {

    private ApplicationService applicationService;
    private MonitorProducerService monitoringProducerService;
    private EventService eventService;
    private UserService userService;

    /**
     * @return Результат работы метода getAllApplicationsByEventId в виде коллекции ApplicationDto
     */
    @Operation(summary = "Поиск заявок по id мероприятия")
    @GetMapping("/ByEvent/{id}")
    public List<ApplicationDto> getAllApplicationsByEventId(@PathVariable Long id) {
        if (!ParameterService.getAllApplicationsByEventIdEnabled.getValue()) {
            log.debug("Метод getAllApplicationsByEventId выключен параметром getAllApplicationsByEventIdEnabled = false");
            throw new RuntimeException("Method getAllApplicationsByEventId is disabled by parameter getAllApplicationsByEventIdEnabled");
        }
        log.debug("Получен запрос на поиск заявок с id мероприятия: {}", id);

        Event event = eventService.getOneEvent(id);

        List<Application> applications = applicationService.getAllApplicationsByEventId(id);
        List<ApplicationDto> applicationsDtoId =
                ApplicationMapper.INSTANCE.ApplicationsToDtoList(applications);

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

        return applicationsDtoId;
    }

    /**
     * @return Результат работы метода getAllApplicationsByUserId в виде коллекции ApplicationDto
     */
    @Operation(summary = "Поиск заявок по id пользователя")
    @GetMapping("/ByUser/{id}")
    public List<ApplicationDto> getAllApplicationsByUserId(@PathVariable Long id) {
        if (!ParameterService.getAllApplicationsByUserIdEnabled.getValue()) {
            log.debug("Метод getAllApplicationsByUserId выключен параметром getAllApplicationsByUserIdEnabled = false");
            throw new RuntimeException("Method getAllApplicationsByUserId is disabled by parameter getAllApplicationsByUserIdEnabled");
        }
        log.debug("Получен запрос на поиск заявок с id пользователя: {}", id);

        User user = userService.getOneUser(id).orElse(null);
        if (user == null) {
            log.error("Пользвателя с id {} не существует", id);
            throw new RuntimeException("Пользвателя с id не существует");
        }

        List<Application> applications = applicationService.getAllApplicationsByUserId(id);
        List<ApplicationDto> applicationsDtoByUserId =
                ApplicationMapper.INSTANCE.ApplicationsToDtoList(applications);

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

        return applicationsDtoByUserId;
    }

    /**
     * @return Результат работы метода sendApplication в виде объекта ApplicationDto
     */
    @Operation(summary = "Запрос на отправку зявки")
    @PostMapping
    public ApplicationDto sendApplication(@RequestBody RequestWrapper requestWrapper) {
        if (!ParameterService.sendApplicationEnabled.getValue()) {
            log.debug("Метод sendApplication выключен параметром sendApplicationEnabled = false");
            throw new RuntimeException("Method sendApplication is disabled by parameter sendApplicationEnabled");
        }
        Application applicationCreate = requestWrapper.getApplication();
        User user = requestWrapper.getUser();

        log.debug("Запрос на отправку зявки {}", applicationCreate);

        Application application = applicationService.saveApplication(applicationCreate, user);
        ApplicationDto applicationDto =
                ApplicationMapper.INSTANCE.ApplicationToDto(application);

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

        return applicationDto;
    }
}
