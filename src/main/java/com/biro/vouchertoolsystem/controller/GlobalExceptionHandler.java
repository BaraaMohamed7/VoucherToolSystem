//package com.biro.vouchertoolsystem.controller;
//
//import org.apache.coyote.BadRequestException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.ResponseExtractor;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    @ExceptionHandler(BadRequestException.class)
//    public ErrorResponse handleBadRequestException(BadRequestException e) {
//        return new ErrorResponse(200,e.getMessage());
//    }
//}
