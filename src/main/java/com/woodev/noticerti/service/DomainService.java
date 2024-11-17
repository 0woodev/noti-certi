package com.woodev.noticerti.service;

import com.woodev.noticerti.model.Domain;

import java.net.URL;
import java.util.Optional;

public interface DomainService {
    Optional<Domain> findByHostAndPort(URL httpsUrl);
}
