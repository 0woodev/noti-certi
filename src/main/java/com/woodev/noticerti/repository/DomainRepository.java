package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByHostAndPort(String host, int port);

    Optional<Domain> findByIpAndPort(String ip, int port);

    boolean existsByIpAndPort(String ip, int port);

    List<Domain> findAllByHostContaining(String host);

}
