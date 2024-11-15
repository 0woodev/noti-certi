package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Optional<Certificate> findByIssuingCAAndSerialNumber(String issuingCA, String serialNumber);

}
