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
import ru.team.up.core.exception.notFoundException.UserNotFoundIDException;
import ru.team.up.core.mappers.InterestsMapper;
import ru.team.up.input.response.InterestsDtoResponse;
import ru.team.up.input.service.InterestServiceRest;

import java.util.*;

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
        log.debug("Получен запрос на список всех интересов");
        List<Interests> listInterests = interestsServiceRest.getAllInterests();
        ArrayList<InterestsDtoResponse> interestsDtoResponseList = new ArrayList<>();
        for (Interests interests : listInterests) {
            InterestsDtoResponse interestsDtoResponse = InterestsDtoResponse.builder().interestsDto(
                    InterestsMapper.INSTANCE.mapInterestsToDto((Interests) interests)).build();
            interestsDtoResponseList.add(interestsDtoResponse);
        }
        if (interestsDtoResponseList.isEmpty()) {
            log.debug("Список интересов пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.debug("Список интересов получен");
        return new ResponseEntity<>(interestsDtoResponseList, HttpStatus.OK);
    }

    @Operation(summary = "Получить список интересов по id")
    @GetMapping("/user/interest/{id}")
    public ResponseEntity<List<InterestsDtoResponse>> getInterestsUserById(@PathVariable("id") Long interestsId) {
        log.debug("Получен запрос на список интересов пользователя по id");
        List<Interests> interestsListById = interestsServiceRest.getInterestById(interestsId);
        if (interestsListById.isEmpty()) {
                throw new UserNotFoundIDException(interestsId);
        }
        List<InterestsDtoResponse> listInterestsUserById = new ArrayList<>();
        for (Interests interests : interestsListById) {
            InterestsDtoResponse interestsDtoResponse = InterestsDtoResponse.builder().interestsDto(
                    InterestsMapper.INSTANCE.mapInterestsToDto((Interests) interests)).build();
            listInterestsUserById.add(interestsDtoResponse);
        }
        if (listInterestsUserById.isEmpty()) {
            log.debug("Список интересов пуст");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.debug("Список интересов получен");
        return new ResponseEntity<>(listInterestsUserById, HttpStatus.OK);
    }
}

//    public static void main(String[] args) {
//        List<String> stringList = new LinkedList<>();
//        stringList.add("1");
//        stringList.add("2");
//        stringList.add("3");
//
//        List<Integer> intList = new ArrayList<>();
//
//        for (String s : stringList) {
//            System.out.println(s);
//            Integer i = mapper(s);
//            intList.add(i);
//        }
//
//
//        List<ResponseEntity<Integer>> integerList = stringList.stream().map(s -> {
//            System.out.println(s);
//            Integer i = mapper(s);
//
//            return new ResponseEntity<Integer>(i, HttpStatus.OK);
//        }).collect(Collectors.toList());
//        System.out.println(integerList);
//    }
//
//
//    private static Integer mapper(String s) {
//        return Integer.valueOf(s);
//    }
//        log.debug("Получен запрос на список всех интересов");
//        List<Interests> interestsList = interestsServiceRest.getAllInterests();
////        ResponseEntity<List<InterestsDtoResponse>>listResponseEntity =
//        return interestsList
//                .stream()
//                .map(interests -> {
//                    InterestsDtoResponse interestsDtoResponse = InterestsDtoResponse.builder().interestsDto(
//                            InterestsMapper.INSTANCE.mapInterestsToDto((Interests) interests)).build();
//                    List<InterestsDtoResponse> interestsDtoResponseList = new ArrayList<>();
//                    interestsDtoResponseList.add(interestsDtoResponse);
//                    return new ResponseEntity<List<InterestsDtoResponse>>(interestsDtoResponseList, HttpStatus.OK);
//                });
//    }
//}


