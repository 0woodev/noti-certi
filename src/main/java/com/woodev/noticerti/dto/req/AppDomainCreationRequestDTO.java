package com.woodev.noticerti.dto.req;

import com.woodev.noticerti.model.App;
import com.woodev.noticerti.model.AppDomain;
import com.woodev.noticerti.model.Domain;

//TODO null 금지
public record AppDomainCreationRequestDTO(
        Long appId,
        Long domainId
        ) {
    public AppDomain toEntity(App app, Domain domain) {
        return AppDomain.builder()
                .app(app)
                .domain(domain)
                .build();
    }
}