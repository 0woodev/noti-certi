package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainRepository extends JpaRepository<Domain, Long> {
}
