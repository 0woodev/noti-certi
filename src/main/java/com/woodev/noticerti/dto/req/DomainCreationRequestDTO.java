package com.woodev.noticerti.dto.req;


import com.woodev.noticerti.model.Domain;

public record DomainCreationRequestDTO(
        String host,
        int port,
        String ip
) {

    public Domain toEntity() {
        return Domain.builder()
                .host(host)
                .port(port)
                .ip(ip)
                .build();
    }
}
