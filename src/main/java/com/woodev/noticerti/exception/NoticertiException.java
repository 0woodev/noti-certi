package com.woodev.noticerti.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoticertiException extends RuntimeException {
    private final String msg;
    private final HttpStatus status;

    public NoticertiException(String message) {
        super(message);
        this.msg = message;
        status = HttpStatus.BAD_REQUEST;
    }

    public NoticertiException(String message, HttpStatus status) {
        super(message);
        this.msg = message;
        this.status = status;
    }
}
