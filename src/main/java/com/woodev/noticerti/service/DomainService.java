package com.woodev.noticerti.service;

import com.woodev.noticerti.dto.DomainDTO;
import com.woodev.noticerti.model.Domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DomainService {
    Optional<Domain> findByIpAndPort(String ip, int port);

    Domain getByIpAndPort(String ip, int port);

    Domain getById(Long id);

    String getIpFromDNS(String host, int port) throws MalformedURLException;

    Domain save(Domain entity);

    List<Domain> findAllByHostContaining(String host);

    List<Domain> findAll();

    List<Domain> findAllByAppId(Long appId);
}
