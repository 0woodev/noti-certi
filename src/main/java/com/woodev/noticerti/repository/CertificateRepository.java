package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
