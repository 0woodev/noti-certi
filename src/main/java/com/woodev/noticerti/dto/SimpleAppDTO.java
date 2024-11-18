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
public class SimpleAppDTO {
    private Long id;
    private String appName;
    private String code;
    private String description;
    private Long teamId;

    public SimpleAppDTO(App app) {
        this.id = app.getId();
        this.appName = app.getAppName();
        this.code = app.getCode();
        this.description = app.getDescription();
        this.teamId = app.getTeam().getId();
    }
}
