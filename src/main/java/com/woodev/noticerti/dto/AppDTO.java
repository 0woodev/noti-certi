package com.woodev.noticerti.dto;

import com.woodev.noticerti.model.App;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppDTO {
    private Long id;
    private String appName;
    private String code;
    private String description;
    private TeamDTO team;

    public AppDTO(App app) {
        this.id = app.getId();
        this.appName = app.getAppName();
        this.code = app.getCode();
        this.description = app.getDescription();
        this.team = new TeamDTO(app.getTeam());
    }
}
