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

        ResponseEntity<List<Application>> responseEntity = ResponseEntity.ok(applicationService.getAllApplicationsByEventId(id));

        return responseEntity;
    }

    @GetMapping("/ByUser/{id}")
    public ResponseEntity<List<Application>> getAllApplicationsByUserId(@PathVariable Long id) {

        ResponseEntity<List<Application>> responseEntity = ResponseEntity.ok(applicationService.getAllApplicationsByUserId(id));

        return responseEntity;
    }

    @PostMapping
    public ResponseEntity<Application> sendApplication(@RequestParam User user, @RequestBody @NotNull Application applicationCreate) {

        ResponseEntity<Application> responseEntity = new ResponseEntity<>(applicationService.saveApplication(applicationCreate,user), HttpStatus.CREATED);

        return responseEntity;
    }
}
