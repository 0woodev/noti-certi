package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.AppDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppDomainRepository extends JpaRepository<AppDomain,Long> {
}
