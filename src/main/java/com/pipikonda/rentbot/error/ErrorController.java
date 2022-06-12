package com.pipikonda.rentbot.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;

@RestControllerAdvice
public class ErrorController {


    @ExceptionHandler(BasicLogicException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBasicLogic(BasicLogicException ex) {

        return ErrorResponse.builder()
                .errorCode(ex.getErrorCode())
                .errorText(ex.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(MethodArgumentNotValidException ex) {

        return ErrorResponse.builder()
                .errorCode(ErrorCode.VALIDATION_ERROR)
                .errorText(ex.getMessage())
                .build();
    }
}
