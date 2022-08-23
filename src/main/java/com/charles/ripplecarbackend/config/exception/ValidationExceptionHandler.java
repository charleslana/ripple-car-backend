package com.charles.ripplecarbackend.config.exception;

import com.charles.ripplecarbackend.config.exception.dto.ApiErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        Optional<Map.Entry<String, String>> optional = errorMap.entrySet().stream().findFirst();
        String message = null;
        if (optional.isPresent()) {
            message = String.format(optional.get().getKey().concat(": %s"), optional.get().getValue());
        }
        ApiErrorDTO error = new ApiErrorDTO(HttpStatus.BAD_REQUEST.toString(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
