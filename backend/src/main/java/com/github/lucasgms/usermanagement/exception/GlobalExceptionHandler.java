package com.github.lucasgms.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> handleBusinessValidation(BusinessException ex) {
        Map<String, String> error = buildErrorResponse(ex.getMessage());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String msg = null;

        if (ex.getCause().getCause() instanceof BusinessException)
            msg = ex.getCause().getCause().getMessage();
        else
            msg = ex.getMessage();

        Map<String, String> error = buildErrorResponse(msg);


        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
        Map<String, String> error = buildErrorResponse("Acesso negado. Cheque suas permissões");

        ex.printStackTrace();

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> defaultHandleBusinessValidation(Exception ex) {
        Map<String, String> error = buildErrorResponse("Erro! Entrar em contato com o suporte técnico!");

        ex.printStackTrace();

        return ResponseEntity.badRequest().body(error);
    }

    private Map<String, String> buildErrorResponse(String msg) {
        Map<String, String> error = new HashMap<>();

        error.put("error", msg);
        error.put("time", Instant.now().toString());
        error.put("code", HttpStatus.BAD_REQUEST.toString());

        return error;
    }
}
