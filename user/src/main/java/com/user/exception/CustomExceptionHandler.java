package com.user.exception;

import com.user.response.error.ServerErrorResponse;
import com.user.response.error.ValidationErrorResponse;
import java.util.Arrays;
import java.util.List;
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
    ValidationErrorResponse response = new ValidationErrorResponse(Arrays.asList(ex.getMessage()));
    return new ResponseEntity<>(response.body(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<Object> handleValidationException() {
    ServerErrorResponse response = new ServerErrorResponse();
    return new ResponseEntity<>(response.body(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

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

    ValidationErrorResponse response = new ValidationErrorResponse();
    response.addErrors(allErrors);

    return new ResponseEntity<>(response.body(), HttpStatus.BAD_REQUEST);
  }
}
