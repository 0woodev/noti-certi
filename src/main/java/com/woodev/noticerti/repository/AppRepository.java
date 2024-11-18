package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppRepository extends JpaRepository<App, Long> {
    @Query("SELECT a FROM App a JOIN FETCH a.team WHERE a.appName LIKE %:appName% AND a.code = :code")
    List<App> findAllByAppNameContainingAndCode(String appName, String code);

    @Query("SELECT a FROM App a JOIN FETCH a.team WHERE a.appName LIKE %:appName%")
    List<App> findAllByAppNameContaining(String appName);
}
