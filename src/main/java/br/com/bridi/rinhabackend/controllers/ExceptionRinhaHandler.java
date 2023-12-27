package br.com.bridi.rinhabackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionRinhaHandler {

  @ExceptionHandler(UnprocessableException.class)
  public ResponseEntity<Void> conflict() {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
  }
  
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Void> badRequest() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
  }
  
}
