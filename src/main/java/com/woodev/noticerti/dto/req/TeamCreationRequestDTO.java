package com.woodev.noticerti.dto.req;

import com.woodev.noticerti.model.Team;

public record TeamCreationRequestDTO(
        String teamName,
        String description,

        String teamLeader
) {
    public Team toEntity() {
        return Team.builder()
                .teamName(teamName)
                .description(description)
                .teamLeader(teamLeader)
                .build();
    }
}
