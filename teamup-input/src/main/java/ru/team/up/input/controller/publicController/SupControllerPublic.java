package ru.team.up.input.controller.publicController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.dto.ControlDto;
import ru.team.up.sup.service.KafkaSupService;
import ru.team.up.sup.service.ParameterService;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Tag(name = "Sup Private Controller", description = "Kafka API")
@RestController
@RequestMapping("api/public/sup")
public class SupControllerPublic {
    private KafkaSupService kafkaSupService;
    private MonitorProducerService monitoringProducerService;

    @PostMapping("/get")
    public ResponseEntity get()
    {
        kafkaSupService.getAllModuleParameters();

        monitoringProducerService.send(
                monitoringProducerService.constructReportDto(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(), ControlDto.MANUAL,
                        this.getClass(), null));

        if (!ParameterService.getEnabled.getValue()) {
            log.debug("Метод get выключен параметром getEnabled = false");
            throw new RuntimeException("Method get is disabled by parameter getEnabled");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}