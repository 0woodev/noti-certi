package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.SAN;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SANRepository extends JpaRepository<SAN, Long> {
    List<SAN> findAllByCertificateId(Long certificateId);
}
