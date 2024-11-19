package com.woodev.noticerti.service;

import com.woodev.noticerti.model.Domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public interface DomainService {
    Optional<Domain> findByHostAndPort(URL httpsUrl);

    Domain getById(Long id);

    String getIpFromDNS(String host, int port) throws MalformedURLException;
}
