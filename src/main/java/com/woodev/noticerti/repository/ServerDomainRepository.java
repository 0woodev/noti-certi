package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.ServiceDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerDomainRepository extends JpaRepository<ServiceDomain, Long> {
}
