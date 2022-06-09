package ru.team.up.input.controller.publicController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.team.up.core.mappers.InterestsMapper;
import ru.team.up.core.monitoring.service.MonitorProducerService;
import ru.team.up.dto.ControlDto;
import ru.team.up.input.response.InterestsDtoListResponse;
import ru.team.up.input.response.InterestsDtoResponse;
import ru.team.up.input.service.InterestServiceRest;
import ru.team.up.sup.service.ParameterService;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Tag(name = "Interest Public Controller", description = "Interest API")
@RestController
@RequestMapping("public")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InterestRestControllerPublic {

    private InterestServiceRest interestsServiceRest;
    private MonitorProducerService monitorProducerService;

    /**
     * Метод получения всех интересов
     *
     * @return Список интересов
     */
    @Operation(summary = "Получить список интересов")
    @GetMapping("/interest")
    public InterestsDtoListResponse getInterestsList() {
        log.debug("Получение запроса на список интересов");

        InterestsDtoListResponse interests = InterestsDtoListResponse.builder().interestsDtoList(
                InterestsMapper.INSTANCE.mapInterestsToDtoList(
                        interestsServiceRest.getAllInterests())).build();

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("Количество интересов", interests.getInterestsDtoList().size());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return interests;
    }

    /**
     * Метод получения интереса по Id
     *
     * @return Интерес по заданному Id и статус ответа
     */
    @Operation(summary = "Получить интерес по id")
    @GetMapping("/user/interest/{id}")
    public InterestsDtoResponse getInterestsUserById(@PathVariable("id") Long interestsId) {
        log.debug("Получение запроса на интерес по id: {}", interestsId);
        if (!ParameterService.getInterestsUserByIdEnabled.getValue()) {
            log.debug("Метод getInterestsUserById выключен параметром getInterestsUserByIdEnabled = false");
            throw new RuntimeException("Method getInterestsUserById is disabled by parameter getInterestsUserByIdEnabled");
        }

        InterestsDtoResponse interest = InterestsDtoResponse.builder().interestsDto(
                InterestsMapper.INSTANCE.mapInterestToDto(
                        interestsServiceRest.getInterestById(interestsId))).build();

        Map<String, Object> monitoringParameters = new HashMap<>();
        monitoringParameters.put("ID", interest.getInterestsDto().getId());
        monitoringParameters.put("Название", interest.getInterestsDto().getTitle());

        monitorProducerService.send(
                monitorProducerService.constructReportDto(SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        ControlDto.MANUAL,
                        this.getClass(),
                        monitoringParameters)
        );
        return interest;
    }
}


