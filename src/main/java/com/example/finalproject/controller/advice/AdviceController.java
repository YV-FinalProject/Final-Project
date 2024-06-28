package com.example.finalproject.controller.advice;

import com.example.finalproject.exception.DataNotFoundInDataBaseException;
import com.example.finalproject.exception.InvalidValueExeption;
import com.example.finalproject.exception.UnauthorizedDataException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class AdviceController {


    @ExceptionHandler(DataNotFoundInDataBaseException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(DataNotFoundInDataBaseException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(InvalidValueExeption.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(InvalidValueExeption exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(UnauthorizedDataException.class)
    public ResponseEntity<ErrorMessage> exceptionHandler(UnauthorizedDataException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorMessage(exception.getMessage()));
    }


// для обработки ошибок Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return new ResponseEntity<>(getErrorsMap(errors), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    private Map<String, List<String>> getErrorsMap(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>();
        errorResponse.put("errors", errors);
        return errorResponse;
    }

// по умолчанию для всех остальных исключений
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorMessage> exceptionHandler(Exception exception) {
            return ResponseEntity
                    .status(HttpStatus.I_AM_A_TEAPOT)
                    .body(new ErrorMessage("Sorry, something went wrong!"));
        }







}
