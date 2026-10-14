package tn.esprit.raniaselmi_4cce10.web.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tn.esprit.raniaselmi_4cce10.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return reponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        return reponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadable(HttpMessageNotReadableException ex) {
        return reponse(HttpStatus.BAD_REQUEST, "Corps de la requête illisible ou mal formé");
    }

    private static ResponseEntity<Map<String, Object>> reponse(HttpStatus statut, String message) {
        Map<String, Object> corps = new LinkedHashMap<>();
        corps.put("statut", statut.value());
        corps.put("message", message);
        corps.put("horodatage", LocalDateTime.now());
        return ResponseEntity.status(statut).body(corps);
    }
}
