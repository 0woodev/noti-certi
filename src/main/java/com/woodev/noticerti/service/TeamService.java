package com.woodev.noticerti.service;

import com.woodev.noticerti.model.Team;

import java.util.List;

public interface TeamService {
    Team getTeam(Long id);

    Team save(Team newTeam);

    List<Team> findAllByNameContaining(String teamName);
}
