package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.exception.NoticertiException;
import com.woodev.noticerti.model.Team;
import com.woodev.noticerti.repository.TeamRepository;
import com.woodev.noticerti.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService {
    private final TeamRepository teamRepository;

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new NoticertiException("팀이 존재하지 않습니다."));
    }
}
