package ru.team.up.input.controlle.privateController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.service.ModeratorService;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Alexey Tkachenko
 *
 * @link localhost:8080/swagger-ui.html
 * Документация API
 */

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/moderator")
public class ModeratorController {
    private ModeratorService moderatorService;

    /**
     * @return Результат работы метода moderatorService.getAllModerators() в виде коллекции модераторов
     * в теле ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Moderator>> getAllModerators() {
        log.debug("Старт метода ResponseEntity<List<Moderator>> getAllModerators()");

        ResponseEntity<List<Moderator>> responseEntity = ResponseEntity.ok(moderatorService.getAllModerators());
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Значение ID модератора
     * @return Результат работы метода moderatorService.getOneModerator(id) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Moderator> getOneModerator(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Moderator> getOneModerator(@PathVariable Long id) с параметром {}", id);

        ResponseEntity<Moderator> responseEntity = ResponseEntity.ok(moderatorService.getOneModerator(id));
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param moderator Создаваемый объект класса Moderator
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Moderator> createModerator(@RequestParam String moderator, @RequestBody @NotNull Moderator moderatorCreate) {
        log.debug("Старт метода ResponseEntity<Moderator> createModerator(@RequestBody @NotNull Moderator moderator) с параметром {}", moderatorCreate);

        ResponseEntity<Moderator> responseEntity
                = new ResponseEntity<>(moderatorService.saveModerator(moderatorCreate), HttpStatus.CREATED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param moderator Обновляемый объект класса Moderator
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @PatchMapping
    public ResponseEntity<Moderator> updateModerator(@RequestBody @NotNull Moderator moderator) {
        log.debug("Старт метода ResponseEntity<Moderator> updateModerator(@RequestBody @NotNull Moderator moderator) с параметром {}", moderator);

        ResponseEntity<Moderator> responseEntity = ResponseEntity.ok(moderatorService.saveModerator(moderator));
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }

    /**
     * @param id Удаляемого объекта класса Moderator
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Moderator> deleteModerator(@PathVariable Long id) {
        log.debug("Старт метода ResponseEntity<Moderator> deleteModerator(@PathVariable Long id) с параметром {}", id);

        moderatorService.deleteModerator(id);

        ResponseEntity<Moderator> responseEntity = new ResponseEntity<>(HttpStatus.ACCEPTED);
        log.debug("Получили ответ {}", responseEntity);

        return responseEntity;
    }
}
