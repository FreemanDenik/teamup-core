package ru.team.up.core.controller;

import lombok.AllArgsConstructor;
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

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/moderators")
public class ModeratorController {
    private ModeratorService moderatorService;

    /**
     * @return Результат работы метода moderatorService.getAllModerators() в виде коллекции модераторов
     * в теле ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<Moderator>> getAllModerators() {
        return ResponseEntity.ok(moderatorService.getAllModerators());
    }

    /**
     * @param id Значение ID модератора
     * @return Результат работы метода moderatorService.getOneModerator(id) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<Moderator> getOneModerator(@PathVariable Long id) {
        return ResponseEntity.ok(moderatorService.getOneModerator(id));
    }

    /**
     * @param moderator Создаваемый объект класса Moderator
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @PostMapping
    public ResponseEntity<Moderator> createModerator(@RequestBody @NotNull Moderator moderator) {
        return new ResponseEntity<>(moderatorService.saveModerator(moderator), HttpStatus.CREATED);
    }

    /**
     * @param moderator Обновляемый объект класса Moderator
     * @return Результат работы метода moderatorService.saveModerator(moderator) в виде объекта Moderator
     * в теле ResponseEntity
     */
    @PatchMapping
    public ResponseEntity<Moderator> updateModerator(@RequestBody @NotNull Moderator moderator) {
        return ResponseEntity.ok(moderatorService.saveModerator(moderator));
    }

    /**
     * @param id Удаляемого объекта класса Moderator
     * @return Объект ResponseEntity со статусом OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Moderator> deleteModerator(@PathVariable Long id) {
        moderatorService.deleteModerator(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
