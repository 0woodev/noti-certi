package com.woodev.noticerti.dto;

import com.woodev.noticerti.model.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTeamDTO {

    private Long id;
    private String teamName;
    private String teamLeader;

    public SimpleTeamDTO(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.teamLeader = team.getTeamLeader();
    }
}
