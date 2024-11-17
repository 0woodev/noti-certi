package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.App;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRepository extends JpaRepository<App, Long> {
}
