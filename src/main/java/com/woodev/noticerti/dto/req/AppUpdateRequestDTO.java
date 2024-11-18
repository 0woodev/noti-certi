package com.woodev.noticerti.dto.req;

import com.woodev.noticerti.model.App;
import com.woodev.noticerti.model.Team;

// TODO validation annotations 추가
public record AppUpdateRequestDTO(

        Long id,
        String name,
        String code,
        String description,
        Long teamId
) {

    public App toEntity(Team team) {
        return App.builder()
                .id(id)
                .appName(name)
                .code(code)
                .description(description)
                .team(team)
                .build();
    }
}
