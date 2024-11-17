package com.woodev.noticerti.aop;
import com.woodev.noticerti.dto.res.ResponseDTO;
import com.woodev.noticerti.exception.NoticertiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseDTO<Object>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDTO.builder()
                        .data(null)
                        .message(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(NoticertiException.class)
    public ResponseEntity<ResponseDTO<Object>> handleNoticertiException(NoticertiException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ResponseDTO.builder()
                        .data(null)
                        .message("[%s] - %s".formatted(ex.getClass().getName(), ex.getMessage()))
                        .build());
    }
}
