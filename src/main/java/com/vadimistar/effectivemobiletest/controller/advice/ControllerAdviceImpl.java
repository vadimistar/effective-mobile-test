package com.vadimistar.effectivemobiletest.controller.advice;

import com.vadimistar.effectivemobiletest.dto.ErrorDto;
import com.vadimistar.effectivemobiletest.exception.EmailAlreadyExistsException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException e) {
        ErrorDto errorDto = new ErrorDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception e) {
        log.error("Exception occurred ({}): {}", e.getClass().toString(), e.getMessage());
        ErrorDto errorDto = new ErrorDto("Internal server error");
        return ResponseEntity.internalServerError().body(errorDto);
    }
}