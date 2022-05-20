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
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.core.service.AssignedEventsService;
import ru.team.up.core.service.ModeratorService;
import ru.team.up.dto.ControlDto;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexey Tkachenko
 *
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
     * @return Результат работы метода moderatorService.getAllModerators() в виде коллекции модераторов
     * в теле ResponseEntity
     */
    @Operation(summary = "Получение списка всех модераторов")
    @GetMapping
    public ResponseEntity<List<Account>> getAllModerators() {
        log.debug("Старт метода ResponseEntity<List<Moderator>> getAllModerators()");

        List<Account> allModerators = moderatorService.getAllModerators();

        ResponseEntity<List<Account>> responseEntity = ResponseEntity.ok(allModerators);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("Количество модераторов", allModerators.size());

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
     * @return Результат работы метода moderatorService.getOneModerator(id) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @Operation(summary = "Получение модератора по id")
    @GetMapping("/{id}")
    public ResponseEntity<Account> getOneModerator(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Moderator> getOneModerator(@PathVariable Long id) с параметром {}", id);

        Account moderator = moderatorService.getOneModerator(id);
        ResponseEntity<Account> responseEntity = ResponseEntity.ok(moderator);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID", moderator.getId());
        monitoringParameters.put("Email", moderator.getEmail());
        monitoringParameters.put("Имя", moderator.getUsername());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return responseEntity;
    }

    /**
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @Operation(summary = "Создание нового модератора")
    @PostMapping
    public ResponseEntity<Account> createModerator(@RequestBody @NotNull Account moderatorCreate) {
        log.debug("Старт метода ResponseEntity<Moderator> createModerator(@RequestBody @NotNull Moderator moderator) с параметром {}", moderatorCreate);
        ResponseEntity<Account> responseEntity =
                new ResponseEntity<>(moderatorService.saveModerator(moderatorCreate), HttpStatus.CREATED);

        log.debug("Получили ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID", moderatorCreate.getId());
        monitoringParameters.put("Email", moderatorCreate.getEmail());
        monitoringParameters.put("Имя", moderatorCreate.getUsername());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return responseEntity;
    }

    /**
     * @param moderator   Обновляемый объект класса Moderator
     * @param moderatorId id модератора
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @Operation(summary = "Обновление данных модератора")
    @PutMapping("/{id}")
    public ResponseEntity<Moderator> updateModerator(@RequestBody @NotNull Moderator moderator, @PathVariable("id") Long moderatorId) {
        log.debug("Старт метода ResponseEntity<Moderator> updateModerator(@RequestBody @NotNull Moderator moderator) с параметром {}", moderator);
        ResponseEntity<Moderator> responseEntity;
        if (moderatorService.moderatorIsExistsById(moderatorId) && moderator.getId().equals(moderatorId)) {
            responseEntity = ResponseEntity.ok(moderatorService.updateModerator(moderator));
            log.debug("Модератор обновлён {}", responseEntity);

            Map<String, Object> monitoringParameters = new HashMap<>();
            monitoringParameters.put("ID", moderator.getId());
            monitoringParameters.put("Email", moderator.getEmail());
            monitoringParameters.put("Имя", moderator.getUsername());

            monitorProducerService.send(
                    monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                            ControlDto.MANUAL,
                            this.getClass(),
                            monitoringParameters)
            );
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            log.debug("Неверно указан id");
        }
        return responseEntity;
    }

    /**
     * @param id Удаляемого объекта класса Moderator
     * @return Объект ResponseEntity со статусом OK
     */
    @Operation(summary = "Удаление модератора по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Moderator> deleteModerator(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Moderator> deleteModerator(@PathVariable Long id) с параметром {}", id);

        moderatorService.deleteModerator(id);

        ResponseEntity<Moderator> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID", id);

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
    public ResponseEntity<List<Event>> getAssignedEventsOfModerator(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<List<Event>> getAssignedEventsOfModerator(@PathVariable Long id)" +
                " с параметром {}", id);
        List<Event> allEvents = assignedEventsService.getAllEventsByModeratorId(id);

        ResponseEntity<List<Event>> responseEntity = ResponseEntity.ok(allEvents);
        log.debug("Получили ответ {}", responseEntity);

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("Количество мероприятий", allEvents.size());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return responseEntity;
    }
}
