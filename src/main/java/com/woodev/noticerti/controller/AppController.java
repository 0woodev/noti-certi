package com.woodev.noticerti.controller;


import com.woodev.noticerti.dto.AppDTO;
import com.woodev.noticerti.dto.req.AppCreationRequestDTO;
import com.woodev.noticerti.dto.req.AppDomainCreationRequestDTO;
import com.woodev.noticerti.dto.req.AppUpdateRequestDTO;
import com.woodev.noticerti.dto.res.ResponseDTO;
import com.woodev.noticerti.model.App;
import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.model.Team;
import com.woodev.noticerti.service.AppService;
import com.woodev.noticerti.service.DomainService;
import com.woodev.noticerti.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/app")
@RequiredArgsConstructor
@RestController
public class AppController {
    private final AppService appService;
    private final TeamService teamService;
    private final DomainService domainService;

    // Read
    @GetMapping()
    public ResponseDTO<List<AppDTO>> getApp(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(required = false) String code
    ) {
        List<AppDTO> apps = appService.findAll(name, code).stream()
                .map(AppDTO::new)
                .toList();

        return ResponseDTO.<List<AppDTO>>builder()
                .data(apps)
                .message("Success")
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<AppDTO> getApp(@PathVariable Long id) {
        App app = appService.getApp(id);

        return ResponseDTO.<AppDTO>builder()
                .data(new AppDTO(app))
                .message("Success")
                .build();
    }

    @PostMapping
    public ResponseDTO<AppDTO> createApp(@RequestBody AppCreationRequestDTO request) {
        Team team = request.teamId() == null ? null : teamService.getTeam(request.teamId());

        App app = appService.save(request.toEntity(team));
        return ResponseDTO.<AppDTO>builder()
                .data(new AppDTO(app))
                .message("Success")
                .build();
    }

    @PutMapping
    public ResponseDTO<AppDTO> updateApp(@RequestBody AppUpdateRequestDTO request) {
        Team team = request.teamId() == null ? null : teamService.getTeam(request.teamId());

        App app = appService.update(request.toEntity(team));
        return ResponseDTO.<AppDTO>builder()
                .data(new AppDTO(app))
                .message("Success")
                .build();
    }

    // 도메인과 앱이랑 연결
    @PostMapping("/domain")
    public ResponseDTO<Void> updateAppWithDomain(@RequestBody AppDomainCreationRequestDTO request) {
        App app=appService.getApp(request.appId());
        Domain domain=domainService.getDomain(request.domainId());

        appService.saveAppDomain(request.toEntity(app,domain));

        return ResponseDTO.<Void>builder()
                .message("Success")
                .build();
    }


}
