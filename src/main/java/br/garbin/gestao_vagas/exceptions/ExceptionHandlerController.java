package br.garbin.gestao_vagas.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

  private MessageSource messageSource;

  public ExceptionHandlerController(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    List<ErrorMessageDTO> errors = new ArrayList<>();

    ex.getBindingResult().getFieldErrors().forEach(error -> {
      String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
      errors.add(new ErrorMessageDTO(error.getField(), message));
    });

    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }
}
