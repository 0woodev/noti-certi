package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByHostAndPort(String host, int port);
}
