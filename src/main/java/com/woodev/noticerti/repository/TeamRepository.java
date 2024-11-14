package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
