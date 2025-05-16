package vn.lottefinance.pdms_core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vn.lottefinance.pdms_core.common.BaseResponseDTO;
import vn.lottefinance.pdms_core.exception.RestException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(RestException.class)
    public ResponseEntity<BaseResponseDTO> handleOkHttpClientException(RestException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(ex.getStatusCode()).body(
              BaseResponseDTO.builder()
                      .timestamp(LocalDateTime.now())
                      .status(ex.getStatusCode().value())
                      .code(ex.getReason())
                      .message("Something when wrong")
                      .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(BaseResponseDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(400)
                .code("400")
                .message("Bad request")
                .errors(errors)
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponseDTO> handleGlobalException(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
               BaseResponseDTO.builder()
                       .timestamp(LocalDateTime.now())
                       .status(500)
                       .code("500")
                       .message("System error")
                       .build()
        );
    }
}
