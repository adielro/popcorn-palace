package com.att.tdp.popcorn_palace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Centralized error handler to format exceptions across the app.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle generic RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntime(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 400,
                        "error", ex.getMessage()
                ));
    }

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(Map.of(
            "timestamp", LocalDateTime.now(),
            "status", 400,
            "errors", errors
        ));
    }

    // Handle MovieAlreadyExistsException
    @ExceptionHandler(MovieAlreadyExistsException.class)
    public ResponseEntity<?> handleMovieAlreadyExists(MovieAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 400,
                        "error", ex.getMessage()
                ));
    }

    // Handle MovieNotFoundException
    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<?> handleMovieNotFound(MovieNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 404,
                        "error", ex.getMessage()
                ));
    }

    // Handle InvalidStartEndTimeException
    @ExceptionHandler(InvalidStartEndTimeException.class)
    public ResponseEntity<?> handleInvalidStartEndTime(InvalidStartEndTimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 400,
                        "error", ex.getMessage()
                ));
    }

    // Handle ShowtimeDurationException
    @ExceptionHandler(ShowtimeDurationException.class)
    public ResponseEntity<?> handleShowtimeDuration(ShowtimeDurationException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 400,
                        "error", ex.getMessage()
                ));
    }

    // Handle ShowtimeNotFoundException
    @ExceptionHandler(ShowtimeNotFoundException.class)
    public ResponseEntity<?> handleShowtimeNotFound(ShowtimeNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 404,
                        "error", ex.getMessage()
                ));
    }

    // Handle SeatAlreadyTakenException
    @ExceptionHandler(SeatAlreadyTakenException.class)
    public ResponseEntity<?> handleSeatAlreadyTaken(SeatAlreadyTakenException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 400,
                        "error", ex.getMessage()
                ));
    }

    // Handle ShowtimeOverlapException
    @ExceptionHandler(ShowtimeOverlapException.class)
    public ResponseEntity<?> handleShowtimeOverlap(ShowtimeOverlapException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of(
                        "timestamp", LocalDateTime.now(),
                        "status", 409,
                        "error", ex.getMessage()
                ));
    }
}