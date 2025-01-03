package com.user.exception;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<Object> handleValidationException(ValidationException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", "Validation failed");
    body.put("errors", Arrays.asList(ex.getMessage()));

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<Object> handleValidationException(InternalServerException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", "Something went wrong, please try again");

    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", "Validation failed");

    List<String> fieldErrors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();

    List<String> globalErrors =
        ex.getBindingResult().getGlobalErrors().stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();

    List<String> allErrors =
        Stream.concat(fieldErrors.stream(), globalErrors.stream()).collect(Collectors.toList());

    body.put("errors", allErrors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
