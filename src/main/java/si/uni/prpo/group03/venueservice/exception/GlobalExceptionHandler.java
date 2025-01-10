package si.uni.prpo.group03.venueservice.exception;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "Invalid input or missing required fields"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ApiResponse(responseCode = "405", description = "HTTP method not supported")
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String message = String.format("HTTP method '%s' not supported for this endpoint. Supported methods are: %s",
                                        ex.getMethod(), ex.getSupportedHttpMethods());
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(DataAccessException.class)
    @ApiResponse(responseCode = "500", description = "Database access error")
    public ResponseEntity<String> handleDataAccessException(DataAccessException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("An error occurred while accessing the database.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ApiResponse(responseCode = "400", description = "Constraint violation, such as unique constraint")
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Invalid data: a unique constraint was violated.");
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Reservation not found")
    public ResponseEntity<String> handleReservationNotFoundException(ReservationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ReservationConflictException.class)
    @ApiResponse(responseCode = "409", description = "Reservation conflict occurred")
    public ResponseEntity<String> handleReservationConflictException(ReservationConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(VenueNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Venue not found")
    public ResponseEntity<String> handleVenueNotFoundException(VenueNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserServiceException.class)
    @ApiResponse(responseCode = "503", description = "User service unavailable")
    public ResponseEntity<String> handleUserServiceException(UserServiceException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Unexpected internal server error")
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }

    @ExceptionHandler(ConcurrentUpdateException.class)
    @ApiResponse(responseCode = "409", description = "Concurrent update conflict")
    public ResponseEntity<String> handleConcurrentUpdateException(ConcurrentUpdateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());          
    }
}
