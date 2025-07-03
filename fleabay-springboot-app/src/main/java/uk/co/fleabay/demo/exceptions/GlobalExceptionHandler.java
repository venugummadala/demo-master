package uk.co.fleabay.demo.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import uk.co.fleabay.demo.exceptions.InvalidURLException;
import uk.co.fleabay.demo.exceptions.ProductNotFoundException;

import java.net.MalformedURLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFound(ProductNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<String> handleMalformedURLException(MalformedURLException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonProcessingException(JsonProcessingException ex) {
        return new ResponseEntity<>("JSON processing error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidURLException.class)
    public ResponseEntity<String> handleInvalidURLException(InvalidURLException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}