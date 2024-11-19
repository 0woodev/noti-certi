package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.AppDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppDomainRepository extends JpaRepository<AppDomain, Long> {
    @Query("SELECT ad FROM AppDomain ad JOIN FETCH ad.domain d WHERE ad.app.id = :appId")
    List<AppDomain> findAllByAppId(Long appId);
}
