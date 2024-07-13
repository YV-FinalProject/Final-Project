package com.example.finalproject.controller.advice;

import com.example.finalproject.exception.*;
import jakarta.validation.*;
import org.modelmapper.spi.*;
import org.springframework.context.support.*;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.*;

import java.util.*;
import java.util.stream.*;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(DataNotFoundInDataBaseException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(DataNotFoundInDataBaseException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(DataAlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(OrderStatusException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(OrderStatusException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorMessage(exception.getMessage()));
    }

    // для обработки ошибок Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // обработчик аннотаций параметра id в контроллерах
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        List<String> errors = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    // обработчик несовпадения типов для параметра id контроллерах
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errors = new HashMap<>();
        String parameterName = ex.getParameter().getParameterName();
        String errorMessage = "Invalid parameter type";
        if (ex.getRequiredType() != null) {
            errorMessage = String.format("The parameter '%s' should be of type '%s'", parameterName, ex.getRequiredType().getSimpleName());
        }
        errors.put("errors", errorMessage);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> errors = new HashMap<>();
        String exceptionMessage = ex.getMessage();
        String errorMessage = "Invalid parameter type";
        if (ex.getMessage() != null) {
            errorMessage = String.format(exceptionMessage);
        }
        errors.put("errors", errorMessage);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }





    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

    // по умолчанию для всех остальных исключений
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorMessage> exceptionHandler(Exception exception) {
//        return ResponseEntity
//                .status(HttpStatus.I_AM_A_TEAPOT)
//                .body(new ErrorMessage("Sorry, something went wrong!"));
//    }

}
