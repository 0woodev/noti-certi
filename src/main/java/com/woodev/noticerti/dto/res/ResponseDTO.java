package com.woodev.noticerti.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDTO<T> {

    private final T data;
    private final String message;
    @Builder.Default
    private HttpStatus status = HttpStatus.OK;
}