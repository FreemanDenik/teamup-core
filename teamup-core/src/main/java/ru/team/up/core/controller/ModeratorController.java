package ru.team.up.core.controller;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.team.up.core.entity.Moderator;
import ru.team.up.core.repositories.ModeratorRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexey Tkachenko
 */

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/private/account/moderators")
public class ModeratorController {
    private ModeratorRepository moderatorRepository;

    @GetMapping
    public ResponseEntity<List<Moderator>> getAllModerators() {
        try {
            List<Moderator> allModerators = new ArrayList<>(moderatorRepository.findAll());

            if (allModerators.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(allModerators, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moderator> getOneModerator(@PathVariable Long id) {
        Optional<Moderator> moderator = moderatorRepository.findById(id);
        return moderator.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Moderator> createModerator(@RequestBody Moderator moderator) {
        try {
            Moderator newModerator = moderatorRepository.save(moderator);
            return new ResponseEntity<>(newModerator, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Moderator> updateModerator(@RequestBody @NotNull Moderator moderator) {
        Optional<Moderator> moderatorOptional = moderatorRepository.findById(moderator.getId());
        if (moderatorOptional.isPresent()) {
            Moderator updateModerator = moderatorRepository.save(moderator);
            return new ResponseEntity<>(updateModerator, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity<Moderator> deleteModerator(@RequestBody Moderator moderator) {
        try {
            moderatorRepository.delete(moderator);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
