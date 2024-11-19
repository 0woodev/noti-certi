package com.woodev.noticerti.dto;

import com.woodev.noticerti.model.Domain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DomainDTO {
    private Long id;
    private String host;
    private int port;
    private String ip;
    private Long certificateId;

    public DomainDTO(Domain entity) {
        this.id = entity.getId();
        this.host = entity.getHost();
        this.port = entity.getPort();
        this.ip = entity.getIp();
        this.certificateId = entity.getCertificate().getId();
    }
}
