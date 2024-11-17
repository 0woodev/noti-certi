package com.woodev.noticerti.aop;

import com.woodev.noticerti.dto.res.ResponseDTO;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Component
@ControllerAdvice
public class ResponseEntityWrapperAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // ResponseDTO만 처리
        return ResponseDTO.class.isAssignableFrom(returnType.getParameterType());
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        // ResponseDTO를 ResponseEntity로 변환
        if (body instanceof ResponseDTO) {
            return ResponseEntity
                    .status(((ResponseDTO<?>) body).getStatus())
                    .body(body);
        }

        return body;
    }
}
