package com.woodev.noticerti.dto.req;

import com.woodev.noticerti.model.Team;

public record TeamUpdateRequestDTO(
        Long id,
        String teamName,
        String description,

        String teamLeader
) {
    public Team toEntity() {
        return Team.builder()
                .id(id)
                .teamName(teamName)
                .description(description)
                .teamLeader(teamLeader)
                .build();
    }
}
