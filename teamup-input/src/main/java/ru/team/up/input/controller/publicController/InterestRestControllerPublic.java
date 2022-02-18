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

import ru.team.up.core.entity.Interests;
import ru.team.up.core.exception.NoContentException;
import ru.team.up.core.exception.notFoundException.UserNotFoundIDException;
import ru.team.up.core.mappers.InterestsMapper;
import ru.team.up.input.response.InterestsDtoResponse;
import ru.team.up.input.service.InterestServiceRest;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Tag(name = "Interest Public Controller", description = "Interest API")
@RestController
@RequestMapping("public")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class InterestRestControllerPublic {

    private final InterestServiceRest interestsServiceRest;

    @Operation(summary = "Получить список интересов")
    @GetMapping("/interest")
    public ResponseEntity<List<InterestsDtoResponse>> getInterestsList() {
        log.debug("Получен запрос на список интересов");
        List<Interests> interestsList = interestsServiceRest.getAllInterests();
        List<InterestsDtoResponse> interestsDtoResponseList =
                interestsList
                        .stream()
                        .map(interests -> (InterestsDtoResponse.builder().interestsDto(InterestsMapper
                                .INSTANCE.mapInterestsToDto((Interests) interests)).build()))
                        .collect(Collectors.toList());
        if (interestsDtoResponseList.isEmpty()) {
            log.debug("Список интересов пуст");
            throw new NoContentException();
        }
        log.debug("Список интересов получен");
        return new ResponseEntity<List<InterestsDtoResponse>>(interestsDtoResponseList, HttpStatus.OK);
    }

    @Operation(summary = "Получить список интересов по id")
    @GetMapping("/user/interest/{id}")
    public ResponseEntity<List<InterestsDtoResponse>> getInterestsUserById(@PathVariable("id") Long interestsId) {
        log.debug("Получен запрос на список интересов пользователя по id");
        List<Interests> interestsListById = interestsServiceRest.getInterestById(interestsId);
        List<InterestsDtoResponse> interestsDtoResponseListById =
                interestsListById
                        .stream()
                        .map(interests -> (InterestsDtoResponse.builder().interestsDto(InterestsMapper
                                .INSTANCE.mapInterestsToDto((Interests) interests)).build()))
                        .collect(Collectors.toList());
        if (interestsDtoResponseListById.isEmpty()) {
            log.debug("Список интересов пуст");
            throw new UserNotFoundIDException(interestsId);
        }
        log.debug("Список интересов получен");
        return new ResponseEntity<List<InterestsDtoResponse>>(interestsDtoResponseListById, HttpStatus.OK);
    }
}


