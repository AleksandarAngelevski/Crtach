package org.example.crtachbackend.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * A Controller advice class used
 * for catching all exceptions
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Exception handler method that
     * handles the entity not found
     * exception
     *
     * @param e - entity not found exception
     *          parameter
     *
     * @return - returns a new response entity
     *          with a status code of not found
     *          and exception message
     */
    @ExceptionHandler(value={EntityNotFoundException.class})
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e){

        return ResponseEntity.
                status(HttpStatus.NOT_FOUND)
                .header("Content-Type", "text/plain;charset=UTF-8")
                .body(e.getMessage());
    }

    /**
     * Exception handler method that
     * handles the illegal argument
     * exception
     *
     * @param e - the illegal argument exception
     *          parameter
     *
     * @return - returns a new response entity
     *          with a status code of bad
     *          request and exception message
     */
    @ExceptionHandler(value={IllegalArgumentException.class})
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "text/plain;charset=UTF-8")
                .body(e.getMessage());
    }

    /**
     * Exception handler method that
     * handles the username not found
     * exception
     *
     * @param e - the username not found exception
     *          param
     *
     * @return - returns a new response entity
     *          with a status code of bad request
     *          and exception message
     */
    @ExceptionHandler(value={UsernameNotFoundException.class})
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e){

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header("Content-Type", "text/plain;charset=UTF-8")
                .body(e.getMessage());
    }

    /**
     * Exception handler method that
     * handles the entity already exists
     * exception
     *
     * @param e - the entity already exists
     *          exception param
     *
     * @return - returns a new response entity
     *          with a status code of conflict
     *          and exception message
     */
    @ExceptionHandler(value = {EntityExistsException.class})
    public ResponseEntity<String> handleEntityExistsException(EntityExistsException e){

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header("Content-Type", "text/plain;charset=UTF-8")
                .body(e.getMessage());
    }

    /**
     * Exception handler method that
     * handles the method argument
     * not valid exception
     *
     * @param e - the method argument not valid
     *          exception param
     *
     * @return - returns a new response entity
     *          with a status code of bad
     *          request and exception message
     *
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        Map<String,String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach((error)->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName,errorMessage);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler method that
     * handles the access denied
     * exception
     *
     * @param e - the access denied exception
     *          param
     *
     * @return - returns a new response entity
     *          with a status code of access denied
     *          and exception message
     */
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e){

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .header("Content-Type", "text/plain;charset=UTF-8")
                .body(e.getMessage());
    }
}
