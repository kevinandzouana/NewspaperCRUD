package newspapercrud.ui.rest;

import newspapercrud.domain.error.AppError;
import newspapercrud.domain.error.DataBaseError;
import newspapercrud.domain.error.DuplicatedUsernameError;
import newspapercrud.domain.error.ForeignKeyError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_KEY = "error";

    @ExceptionHandler(ForeignKeyError.class)
    public ResponseEntity<Map<String, String>> handleForeignKeyError(ForeignKeyError ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ERROR_KEY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DuplicatedUsernameError.class)
    public ResponseEntity<Map<String, String>> handleDuplicatedUsernameError(DuplicatedUsernameError ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ERROR_KEY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(DataBaseError.class)
    public ResponseEntity<Map<String, String>> handleDataBaseError(DataBaseError ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ERROR_KEY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(AppError.class)
    public ResponseEntity<Map<String, String>> handleAppError(AppError ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ERROR_KEY, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ERROR_KEY, "An unexpected error occurred: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
