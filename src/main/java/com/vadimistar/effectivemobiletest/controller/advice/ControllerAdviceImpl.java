package com.vadimistar.effectivemobiletest.controller.advice;

import com.vadimistar.effectivemobiletest.dto.ErrorDto;
import com.vadimistar.effectivemobiletest.exception.EmailAlreadyExistsException;
import com.vadimistar.effectivemobiletest.exception.InvalidStatusException;
import com.vadimistar.effectivemobiletest.exception.TaskNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Log4j2
public class ControllerAdviceImpl {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleValidationException(MethodArgumentNotValidException e) {
        List<String> errors = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getDefaultMessage())
                .toList();
        String error = String.join(". ", errors);
        return ResponseEntity.badRequest().body(new ErrorDto(error));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException e) {
        List<String> errors = e.getConstraintViolations()
                .stream()
                .map(violation -> String.format("%s: %s", violation.getPropertyPath(), violation.getMessage()))
                .toList();
        String error = String.join(". ", errors);
        return ResponseEntity.badRequest().body(new ErrorDto(error));
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTaskNotFoundException(TaskNotFoundException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (e.getCause() != null && e.getCause().getCause() != null) {
            Throwable cause = e.getCause().getCause();

            if (cause instanceof InvalidStatusException) {
                ErrorDto errorDto = new ErrorDto(cause.getMessage());
                return ResponseEntity.badRequest().body(errorDto);
            }
        }

        log.error("Invalid request format ({}): {}", e.getClass().toString(), e.getMessage());
        ErrorDto errorDto = new ErrorDto("Неверный формат запроса");
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        log.error("Exception occurred ({}): {}", e.getClass().toString(), e.getMessage());
        ErrorDto errorDto = new ErrorDto("Внутренняя ошибка сервера");
        return ResponseEntity.internalServerError().body(errorDto);
    }
}
