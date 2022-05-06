package ru.team.up.input.controller.publicController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private InterestServiceRest interestsServiceRest;

    /**
     * Метод получения всех интересов
     *
     * @return Список интересов
     */
    @Operation(summary = "Получить список интересов")
    @GetMapping("/interest")
    public InterestsDtoListResponse getInterestsList() {
        log.debug("Получение запроса на список интересов");

        return InterestsDtoListResponse.builder().interestsDtoList(
                InterestsMapper.INSTANCE.mapInterestsToDtoList(
                        interestsServiceRest.getAllInterests())).build();
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

        return InterestsDtoResponse.builder().interestsDto(
                InterestsMapper.INSTANCE.mapInterestToDto(
                        interestsServiceRest.getInterestById(interestsId))).build();
    }
}


