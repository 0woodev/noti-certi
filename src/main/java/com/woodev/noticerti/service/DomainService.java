package com.woodev.noticerti.service;

import com.woodev.noticerti.model.Domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

public interface DomainService {
    Optional<Domain> findByIpAndPort(String ip, int port);

    Domain getByIpAndPort(String ip, int port);

    Domain getById(Long id);

    String getIpFromDNS(String host, int port) throws MalformedURLException;

    Domain save(Domain entity);
}
