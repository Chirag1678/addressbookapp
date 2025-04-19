package com.bridgelabz.addressbook.exceptions;

import com.bridgelabz.addressbook.dto.ResponseDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AddressBookExceptionHandler {
    private static final String message = "Exception while processing REST Request";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDTO> handleConstraintViolationException(ConstraintViolationException e) {
        Map<String, String> errors = new HashMap<>();

        e.getConstraintViolations().forEach(constraintViolation -> {
            String fieldName = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.put(fieldName, message);
        });

        ResponseDTO response = new ResponseDTO(
                message,
                HttpStatus.BAD_REQUEST.value(),
                errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        BindingResult result = e.getBindingResult();
        for (FieldError error : result.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ResponseDTO response = new ResponseDTO(
                message,
                HttpStatus.BAD_REQUEST.value(),
                errors
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AddressBookException.class)
    public ResponseEntity<ResponseDTO> handleAddressBookException(AddressBookException e) {
        return new ResponseEntity<>(new ResponseDTO(message, HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleException(Exception ex) {
        ResponseDTO response = new ResponseDTO(
                message,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
