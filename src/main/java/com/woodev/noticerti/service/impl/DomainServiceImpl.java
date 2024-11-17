package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.service.DomainService;

import java.net.URL;
import java.util.Optional;

public class DomainServiceImpl implements DomainService {

    @Override
    public Optional<Domain> findByHostAndPort(URL httpsUrl) {
        return Optional.empty();
    }
}
