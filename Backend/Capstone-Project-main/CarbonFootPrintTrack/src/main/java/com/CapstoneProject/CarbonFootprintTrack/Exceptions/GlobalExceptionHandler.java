package com.CapstoneProject.CarbonFootprintTrack.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<String> handleCityNameNotFoundException(CityNotFoundException ex) {

        String message =  " "+ ex.getMessage();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    @ExceptionHandler(UserIdNotFoundException.class)
    public ResponseEntity<String> handleUserIdNotFoundException(UserIdNotFoundException ex) {

        String message =  " "+ ex.getMessage();
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
