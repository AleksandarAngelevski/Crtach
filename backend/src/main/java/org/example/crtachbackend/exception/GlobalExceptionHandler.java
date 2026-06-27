package org.example.crtachbackend.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
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

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
