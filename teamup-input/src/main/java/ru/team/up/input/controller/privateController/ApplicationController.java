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

import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("private/application")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationController {

    private ApplicationService applicationService;

    @GetMapping("/ByEvent/{id}")
    public ResponseEntity<List<Application>> getAllApplicationsByEventId(@PathVariable Long id) {
        if (!ParameterService.getAllApplicationsByEventIdEnabled.getValue()) {
            log.debug("Метод getAllApplicationsByEventId выключен параметром getAllApplicationsByEventId = false");
            throw new RuntimeException("Method getAllApplicationsByEventId is disabled by parameter getAllApplicationsByEventId");
        }

        ResponseEntity<List<Application>> responseEntity = ResponseEntity.ok(applicationService.getAllApplicationsByEventId(id));

        return responseEntity;
    }

    @GetMapping("/ByUser/{id}")
    public ResponseEntity<List<Application>> getAllApplicationsByUserId(@PathVariable Long id) {
        if (!ParameterService.getAllApplicationsByUserIdEnabled.getValue()) {
            log.debug("Метод getAllApplicationsByUserId выключен параметром getAllApplicationsByUserIdEnabled = false");
            throw new RuntimeException("Method getAllApplicationsByUserId is disabled by parameter getAllApplicationsByUserIdEnabled");
        }

        ResponseEntity<List<Application>> responseEntity = ResponseEntity.ok(applicationService.getAllApplicationsByUserId(id));

        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<Application> sendApplication(@RequestParam User user, @RequestBody @NotNull Application applicationCreate) {
        if (!ParameterService.sendApplicationEnabled.getValue()) {
            log.debug("Метод sendApplication выключен параметром sendApplicationEnabled = false");
            throw new RuntimeException("Method sendApplication is disabled by parameter sendApplicationEnabled");
        }

        ResponseEntity<Application> responseEntity = new ResponseEntity<>(applicationService.saveApplication(applicationCreate,user), HttpStatus.CREATED);

        return responseEntity;
    }
}
