package com.woodev.noticerti.repository;

import com.woodev.noticerti.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findAllByTeamNameContaining(String teamName);
}
