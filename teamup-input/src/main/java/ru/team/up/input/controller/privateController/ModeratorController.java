package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.exception.UserNotFoundIDException;
import ru.team.up.core.mappers.EventMapper;
import ru.team.up.core.mappers.ModeratorMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.AssignedEventsService;
import ru.team.up.core.service.ModeratorService;
import ru.team.up.dto.ControlDto;
import ru.team.up.dto.EventDto;
import ru.team.up.dto.ModeratorDto;
import ru.team.up.dto.ParametersDto;
import ru.team.up.input.response.EventDtoListResponse;
import ru.team.up.input.response.ModeratorDtoListResponse;
import ru.team.up.input.response.ModeratorDtoResponse;
import ru.team.up.sup.service.ParameterService;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexey Tkachenko
 * @link localhost:8080/swagger-ui.html
 * @link http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
 * Документация API
 */

@Slf4j
@Tag(name = "Moderator Private Controller", description = "Moderator API")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/moderator")
public class ModeratorController {

    private ModeratorService moderatorService;
    private AssignedEventsService assignedEventsService;
    private MonitorProducerService monitorProducerService;

    /**
     * @return Результат работы метода moderatorService.getAllModerators() в виде коллекции модераторов,
     * возвращаемых в ModeratorDtoListResponse
     */
    @Operation(summary = "Получение списка всех модераторов")
    @GetMapping
    public ModeratorDtoListResponse getAllModerators() {
        log.debug("Старт метода List<Moderator> getAllModerators()");
        if (!ParameterService.getAllModeratorsEnabled.getValue()) {
            log.debug("Метод getAllModerators выключен параметром getAllModeratorsEnabled = false");
            throw new RuntimeException("Method getAllModerators is disabled by parameter getAllModeratorsEnabled");
        }

        List<ModeratorDto> allModerators = ModeratorMapper.INSTANCE.mapAccountListToModeratorDtoList(moderatorService.getAllModerators());
        log.debug("Получили ответ {}", allModerators);

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto allModeratorsParam = ParametersDto.builder()
                .description("Количество модераторов ")
                .value(allModerators.size())
                .build();

        monitoringParameters.put("Количество модераторов", allModeratorsParam);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return ModeratorDtoListResponse.builder().moderatorDtoList(allModerators).build();
    }

    /**
     * @param id Значение ID модератора
     * @return Результат работы метода moderatorService.getOneModerator(id) в виде объекта ModeratorDtoResponse
     */
    @Operation(summary = "Получение модератора по id")
    @GetMapping("/{id}")
    public ModeratorDtoResponse getOneModerator(@PathVariable Long id) {
        log.debug("Старт метода ModeratorDtoResponse getOneModerator(@PathVariable Long id) с параметром {}", id);
        if (!ParameterService.getOneModeratorEnabled.getValue()) {
            log.debug("Метод getOneModerator выключен параметром getOneModeratorEnabled = false");
            throw new RuntimeException("Method getOneModerator is disabled by parameter getOneModeratorEnabled");
        }

        ModeratorDto moderator = ModeratorMapper.INSTANCE.mapAccountToDto(moderatorService.getOneModerator(id));
        log.debug("Получили ответ {}");

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto moderatorId = ParametersDto.builder()
                .description("ID")
                .value(moderator.getId())
                .build();

        ParametersDto moderatorEmail = ParametersDto.builder()
                .description("Email")
                .value(moderator.getEmail())
                .build();

        ParametersDto moderatorName = ParametersDto.builder()
                .description("Имя")
                .value(moderator.getUsername())
                .build();

        monitoringParameters.put("ID", moderatorId);
        monitoringParameters.put("Email", moderatorEmail);
        monitoringParameters.put("Имя", moderatorName);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return ModeratorDtoResponse.builder().moderatorDto(moderator).build();
    }

    /**
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта
     * ModeratorDtoResponse
     */
    @Operation(summary = "Создание нового модератора")
    @PostMapping
    public ModeratorDtoResponse createModerator(@RequestBody @NotNull Account moderatorCreate) {
        log.debug("Старт метода ModeratorDtoResponse createModerator(@RequestBody @NotNull Moderator moderator) с параметром {}", moderatorCreate);
        if (!ParameterService.createModeratorEnabled.getValue()) {
            log.debug("Метод createModerator выключен параметром createModeratorEnabled = false");
            throw new RuntimeException("Method createModerator is disabled by parameter createModeratorEnabled");
        }
        ModeratorDto moderatorDto = ModeratorMapper.INSTANCE.mapAccountToDto(moderatorService.saveModerator(moderatorCreate));

        log.debug("Получили ответ {}", moderatorDto);

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto moderatorId = ParametersDto.builder()
                .description("ID")
                .value(moderatorCreate.getId())
                .build();

        ParametersDto moderatorEmail = ParametersDto.builder()
                .description("Email")
                .value(moderatorCreate.getEmail())
                .build();

        ParametersDto moderatorName = ParametersDto.builder()
                .description("Имя")
                .value(moderatorCreate.getUsername())
                .build();

        monitoringParameters.put("ID", moderatorId);
        monitoringParameters.put("Email", moderatorEmail);
        monitoringParameters.put("Имя", moderatorName);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return ModeratorDtoResponse.builder().moderatorDto(moderatorDto).build();
    }

    /**
     * @param moderator   Обновляемый объект класса Moderator
     * @param moderatorId id модератора
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта
     * ModeratorDtoResponse
     */
    @Operation(summary = "Обновление данных модератора")
    @PutMapping("/{id}")
    public ModeratorDtoResponse updateModerator(@RequestBody @NotNull Moderator moderator, @PathVariable("id") Long moderatorId) {
        log.debug("Старт метода ModeratorDtoResponse updateModerator(@RequestBody @NotNull Moderator moderator) с параметром {}", moderator);
        if (!ParameterService.updateModeratorEnabled.getValue()) {
            log.debug("Метод updateModerator выключен параметром updateModeratorEnabled = false");
            throw new RuntimeException("Method updateModerator is disabled by parameter updateModeratorEnabled");
        }
        ModeratorDto moderatorDto;
        if (moderatorService.moderatorIsExistsById(moderatorId) && moderator.getId().equals(moderatorId)) {
            moderatorDto = ModeratorMapper.INSTANCE.mapModeratorToDto(moderatorService.updateModerator(moderator));
            log.debug("Модератор обновлён {}", moderatorDto);

            Map<String, ParametersDto> monitoringParameters = new HashMap<>();

            ParametersDto moderatorIdUpdate = ParametersDto.builder()
                    .description("ID")
                    .value(moderator.getId())
                    .build();

            ParametersDto moderatorEmailUpdate = ParametersDto.builder()
                    .description("Email")
                    .value(moderator.getEmail())
                    .build();

            ParametersDto moderatorNameUpdate = ParametersDto.builder()
                    .description("Имя")
                    .value(moderator.getUsername())
                    .build();

            monitoringParameters.put("ID", moderatorIdUpdate);
            monitoringParameters.put("Email", moderatorEmailUpdate);
            monitoringParameters.put("Имя", moderatorNameUpdate);

            monitorProducerService.send(
                    monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                            ControlDto.MANUAL,
                            this.getClass(),
                            monitoringParameters)
            );
        } else {
           throw new UserNotFoundIDException(moderatorId);
        }
        return ModeratorDtoResponse.builder().moderatorDto(moderatorDto).build();
    }

    /**
     * @param id Удаляемого объекта класса Moderator
     * @return Объект ResponseEntity со статусом OK
     */
    @Operation(summary = "Удаление модератора по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Moderator> deleteModerator(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Moderator> deleteModerator(@PathVariable Long id) с параметром {}", id);
        if (!ParameterService.deleteModeratorEnabled.getValue()) {
            log.debug("Метод deleteModerator выключен параметром deleteModeratorEnabled = false");
            throw new RuntimeException("Method deleteModerator is disabled by parameter deleteModeratorEnabled");
        }

        moderatorService.deleteModerator(id);

        ResponseEntity<Moderator> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto deleteModeratorId = ParametersDto.builder()
                .description("ID")
                .value(id)
                .build();
        monitoringParameters.put("ID", deleteModeratorId);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return responseEntity;
    }

    /**
     * @param id Значение ID модератора
     * @return Результат работы метода assignedEventsService.getAllEventsByModeratorId(id) в виде
     * объекта List<AssignedEvents> в теле ResponseEntity
     */
    @Operation(summary = "Получение мероприятий на проверке модератора по его id")
    @GetMapping("/{id}/events")
    public EventDtoListResponse getAssignedEventsOfModerator(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<List<Event>> getAssignedEventsOfModerator(@PathVariable Long id)" +
                " с параметром {}", id);

        if (!ParameterService.getAssignedEventsOfModeratorEnabled.getValue()) {
            log.debug("Метод getAssignedEventsOfModerator выключен параметром getAssignedEventsOfModeratorEnabled = false");
            throw new RuntimeException("Method getAssignedEventsOfModerator is disabled by parameter getAssignedEventsOfModeratorEnabled");
        }
        List<EventDto> eventDtoList = EventMapper.INSTANCE.mapDtoEventToEvent(assignedEventsService.getAllEventsByModeratorId(id));
        log.debug("Получили ответ {}", eventDtoList);

        Map<String, ParametersDto> monitoringParameters = new HashMap<>();

        ParametersDto allEventsOfModerator = ParametersDto.builder()
                .description("Количество мероприятий ")
                .value(eventDtoList.size())
                .build();
        monitoringParameters.put("Количество мероприятий", allEventsOfModerator);

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return EventDtoListResponse.builder().eventDtoList(eventDtoList).build();
    }
}
