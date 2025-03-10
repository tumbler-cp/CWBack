package course.arahnik.dronenotificationlastiteration.controller;

import course.arahnik.dronenotificationlastiteration.exception.NoDroneAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoDroneAvailableException.class)
    public ResponseEntity<String> handleDroneException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
