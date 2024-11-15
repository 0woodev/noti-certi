package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.ServiceDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceDomainRepository extends JpaRepository<ServiceDomain, Long> {
    Optional<ServiceDomain> findByDomainAndPort(String domain, int port);
}
