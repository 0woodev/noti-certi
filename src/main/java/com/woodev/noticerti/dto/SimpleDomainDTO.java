package com.woodev.noticerti.dto;

import com.woodev.noticerti.model.Domain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SimpleDomainDTO {
    private Long id;
    private String host;
    private int port;
    private String ip;

    public SimpleDomainDTO(Domain entity) {
        this.id = entity.getId();
        this.host = entity.getHost();
        this.port = entity.getPort();
        this.ip = entity.getIp();
    }
}
