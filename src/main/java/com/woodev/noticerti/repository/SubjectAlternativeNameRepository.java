package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.SubjectAlternativeName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectAlternativeNameRepository extends JpaRepository<SubjectAlternativeName, Long> {
    List<SubjectAlternativeName> findAllByCertificateId(Long certificateId);
}
