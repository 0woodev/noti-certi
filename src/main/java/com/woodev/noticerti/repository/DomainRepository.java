package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.App;
import com.woodev.noticerti.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DomainRepository extends JpaRepository<Domain, Long> {
    Optional<Domain> findByHostAndPort(String host, int port);

    @Query("SELECT a FROM Domain a JOIN FETCH a.certificate WHERE a.host LIKE %:hostName%")
    List<Domain> findAllByHostNameContaining(String hostName);
}
