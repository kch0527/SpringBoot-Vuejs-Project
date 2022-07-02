package com.shoppingMall.controller;

import com.shoppingMall.exception.InvalidRequest;
import com.shoppingMall.exception.TopException;
import com.shoppingMall.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청")
                .build();

        for (FieldError fieldError : e.getFieldErrors()){
            errorResponse.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return errorResponse;
    }


    @ExceptionHandler(TopException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> TopExceptionExceptionHandler(TopException e){

        int statusCode = e.getStatusCode();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode).body(errorResponse);

        return response;
    }

}
