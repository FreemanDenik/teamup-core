package ru.team.up.input.controller.privateController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Account;
import ru.team.up.core.entity.Event;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.service.AssignedEventsService;
import ru.team.up.core.service.ModeratorService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alexey Tkachenko
 *
 * @link localhost:8080/swagger-ui.html
 * @link  http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config
 * Документация API
 */

@Slf4j
@Tag(name = "Moderator Private Controller",description = "Moderator API")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/moderator")
public class ModeratorController {
    private ModeratorService moderatorService;
    private AssignedEventsService assignedEventsService;


    /**
     * @return Результат работы метода moderatorService.getAllModerators() в виде коллекции модераторов
     * в теле ResponseEntity
     */
    @Operation(summary ="Получение списка всех модераторов")
    @GetMapping
    public ResponseEntity<List<Account>> getAllModerators() {
        log.debug("Старт метода ResponseEntity<List<Moderator>> getAllModerators()");

        ResponseEntity<List<Account>> responseEntity = ResponseEntity.ok(moderatorService.getAllModerators());
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Значение ID модератора
     * @return Результат работы метода moderatorService.getOneModerator(id) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @Operation(summary ="Получение модератора по id")
    @GetMapping("/{id}")
    public ResponseEntity<Account> getOneModerator(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Moderator> getOneModerator(@PathVariable Long id) с параметром {}", id);

        ResponseEntity<Account> responseEntity = ResponseEntity.ok(moderatorService.getOneModerator(id));
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @Operation(summary ="Создание нового модератора")
    @PostMapping
    public ResponseEntity<Account> createModerator(@RequestBody @NotNull Account moderatorCreate) {
        log.debug("Старт метода ResponseEntity<Moderator> createModerator(@RequestBody @NotNull Moderator moderator) с параметром {}", moderatorCreate);
        ResponseEntity<Account> responseEntity
                = new ResponseEntity<>(moderatorService.saveModerator(moderatorCreate), HttpStatus.CREATED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param moderator Обновляемый объект класса Moderator
     * @param moderatorId id модератора
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @Operation(summary ="Обновление данных модератора")
    @PutMapping("/{id}")
    public ResponseEntity<Moderator> updateModerator(@RequestBody @NotNull Moderator moderator,@PathVariable("id") Long moderatorId) {
        log.debug("Старт метода ResponseEntity<Moderator> updateModerator(@RequestBody @NotNull Moderator moderator) с параметром {}", moderator);
        ResponseEntity<Moderator> responseEntity;
        if(moderatorService.moderatorIsExistsById(moderatorId) & moderator.getId().equals(moderatorId)){
            responseEntity = ResponseEntity.ok(moderatorService.updateModerator(moderator));
            log.debug("Модератор обновлён {}", responseEntity);
        }else{
            responseEntity = new ResponseEntity<> (HttpStatus.NO_CONTENT);
        }
        return responseEntity;
    }

    /**
     * @param id Удаляемого объекта класса Moderator
     * @return Объект ResponseEntity со статусом OK
     */
    @Operation(summary ="Удаление модератора по id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Moderator> deleteModerator(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Moderator> deleteModerator(@PathVariable Long id) с параметром {}", id);

        moderatorService.deleteModerator(id);

        ResponseEntity<Moderator> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Значение ID модератора
     * @return Результат работы метода assignedEventsService.getAllEventsByModeratorId(id) в виде
     * объекта List<AssignedEvents> в теле ResponseEntity
     */
    @Operation(summary ="Получение мероприятий на проверке модератора по его id")
    @GetMapping("/{id}/events")
    public ResponseEntity<List<Event>> getAssignedEventsOfModerator(@PathVariable Long id) {

        log.debug("Старт метода ResponseEntity<List<Event>> getAssignedEventsOfModerator(@PathVariable Long id)" +
                " с параметром {}", id);

        ResponseEntity<List<Event>> responseEntity = ResponseEntity.ok(assignedEventsService.getAllEventsByModeratorId(id));

        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }
}
