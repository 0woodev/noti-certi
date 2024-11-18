package com.woodev.noticerti.service;

import com.woodev.noticerti.model.Domain;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public interface DomainService {
    Domain getDomain(Long id);

    Domain save(Domain newDomain);

    List<Domain> findAll(String hostName);

}
