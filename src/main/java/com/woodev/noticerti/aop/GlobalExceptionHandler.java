package com.woodev.noticerti.aop;
import com.woodev.noticerti.dto.res.ResponseDTO;
import com.woodev.noticerti.exception.NoticertiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.net.ssl.SSLHandshakeException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO<Object>> handleRuntimeException(RuntimeException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDTO.builder()
                        .data(null)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(NoticertiException.class)
    public ResponseEntity<ResponseDTO<Object>> handleNoticertiException(NoticertiException ex) {
        String errorMessage = "[%s] - %s".formatted(ex.getClass().getName(), ex.getMessage());
        log.error(errorMessage, ex);
        return ResponseEntity
                .status(ex.getStatus())
                .body(ResponseDTO.builder()
                        .data(null)
                        .message(errorMessage)
                        .build());
    }

    @ExceptionHandler(SSLHandshakeException.class)
    public ResponseEntity<ResponseDTO<Object>> handleSSLHandshakeException(SSLHandshakeException ex) {
        String errorMessage = "[%s] - %s".formatted(ex.getClass().getName(), ex.getMessage());
        log.error(errorMessage, ex);
        return ResponseEntity
                .status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
                .body(ResponseDTO.builder()
                        .data(null)
                        .message(errorMessage)
                        .build());
    }
}
