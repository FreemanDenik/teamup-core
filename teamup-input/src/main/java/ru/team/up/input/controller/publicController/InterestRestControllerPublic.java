package ru.team.up.input.controller.publicController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.team.up.core.mappers.InterestsMapper;
import ru.team.up.input.response.InterestsDtoListResponse;
import ru.team.up.input.response.InterestsDtoResponse;
import ru.team.up.input.service.InterestServiceRest;

@Slf4j
@Tag(name = "Interest Public Controller", description = "Interest API")
@RestController
@RequestMapping("public")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InterestRestControllerPublic {

    private final InterestServiceRest interestsServiceRest;

    /**
     * Метод получения всех интересов
     *
     * @return Список интересов и статус ответа
     */
    @Operation(summary = "Получить список интересов")
    @GetMapping("/interest")
    public ResponseEntity<InterestsDtoListResponse> getInterestsList() {
        log.debug("Получен запрос на список интересов");
        return new ResponseEntity<>(InterestsDtoListResponse.builder().interestsDtoList(
                InterestsMapper.INSTANCE.mapInterestsToDto(
                        interestsServiceRest.getAllInterests())).build(), HttpStatus.OK);
    }

    /**
     * Метод получения интереса по Id
     *
     * @return Интерес по заданному Id и статус ответа
     */
    @Operation(summary = "Получить интерес по id")
    @GetMapping("/user/interest/{id}")
    public ResponseEntity<InterestsDtoResponse> getInterestsUserById(@PathVariable("id") Long interestsId) {
        log.debug("Получен запрос на интерес по id: {}", interestsId);

        return new ResponseEntity<>(InterestsDtoResponse.builder().interestsDto(
                InterestsMapper.INSTANCE.mapInterestToInterestDto(
                        interestsServiceRest.getInterestById(interestsId))).build(), HttpStatus.OK);
    }
}


