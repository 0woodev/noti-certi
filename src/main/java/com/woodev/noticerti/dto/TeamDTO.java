package com.woodev.noticerti.dto;

import com.woodev.noticerti.model.Team;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TeamDTO {
    private Long id;
    private String teamName;
    private String description;
    private String teamLeader;
    private List<SimpleAppDTO> apps;

    public TeamDTO(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.description = team.getDescription();
        this.teamLeader = team.getTeamLeader();
        this.apps = team.getApps().stream().map(SimpleAppDTO::new).toList();
    }
}
