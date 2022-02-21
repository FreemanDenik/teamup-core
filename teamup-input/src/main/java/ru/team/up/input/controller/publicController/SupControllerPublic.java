package ru.team.up.input.controller.publicController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.dto.SupParameterDto;
import ru.team.up.sup.service.SupService;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Tag(name = "Sup Private Controller", description = "Kafka API")
@RestController
@RequestMapping("api/public/sup")
public class SupControllerPublic {
    private SupService supService;



    @PostMapping("/get")
    public ResponseEntity<List<SupParameterDto>> get() {
        return new ResponseEntity<>(supService.getListParameters(), HttpStatus.OK);
    }

}