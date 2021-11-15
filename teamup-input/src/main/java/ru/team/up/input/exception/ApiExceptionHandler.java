//package ru.team.up.input.exception;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
///**
// * @author Pavel Kondrashov on 23.10.2021
// */
//@ControllerAdvice
//public class ApiExceptionHandler {
//
//    @ExceptionHandler(value = {EventCreateRequestException.class})
//    public ResponseEntity<Object> handleApiCreateRequestException(EventCreateRequestException e) {
//        ErrorResponse errorResponse = ErrorResponse
//                .builder()
//                .message(e.getMessage())
//                .status("ERROR")
//                .build();
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(EventCheckException.class)
//    public ResponseEntity<Object> handleApiCheckRequest(EventCheckException e) {
//        ErrorResponse errorResponse = ErrorResponse
//                .builder()
//                .message(e.getMessage())
//                .status("CHECK")
//                .build();
//        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
//    }
//}
