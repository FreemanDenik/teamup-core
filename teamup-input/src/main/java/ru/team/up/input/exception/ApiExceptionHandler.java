package ru.team.up.input.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Pavel Kondrashov on 23.10.2021
 */
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiCreateRequestException.class})
    public ResponseEntity<Object> handleApiCreateRequestException(ApiCreateRequestException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "ERROR");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ApiCheckRequest.class)
    public ResponseEntity<Object> handleApiCheckRequest(ApiCheckRequest e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), "CHECK");
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
}
