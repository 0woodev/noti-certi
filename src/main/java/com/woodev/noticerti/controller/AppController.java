package com.woodev.noticerti.controller;


import com.woodev.noticerti.dto.AppDTO;
import com.woodev.noticerti.dto.req.AppCreationRequestDTO;
import com.woodev.noticerti.dto.req.AppUpdateRequestDTO;
import com.woodev.noticerti.dto.res.ResponseDTO;
import com.woodev.noticerti.model.App;
import com.woodev.noticerti.model.Team;
import com.woodev.noticerti.service.AppService;
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

    @GetMapping("/domain/{id}")
    public ResponseDTO<List<AppDTO>> findAppsByDomainId(@PathVariable(name = "id") Long domainId) {
        List<AppDTO> apps = appService.findAllByDomainId(domainId).stream()
                .map(AppDTO::new)
                .toList();

        return ResponseDTO.<List<AppDTO>>builder()
                .data(apps)
                .message("Success")
                .build();
    }

    @GetMapping("/domain/exclude/{id}")
    public ResponseDTO<List<AppDTO>> findAppsByNotDomainId(@PathVariable(name = "id") Long domainId) {
        List<AppDTO> apps = appService.findAllByNotDomainId(domainId).stream()
                    .map(AppDTO::new)
                    .toList();

        return ResponseDTO.<List<AppDTO>>builder()
                .data(apps)
                .message("Success")
                .build();
    }

    @PutMapping("/domain/{domainId}")
    public ResponseDTO<Integer> connectAppDomain(
            @PathVariable(name = "domainId") Long domainId,
            @RequestParam List<Long> appIds
    ) {
        int updated = appService.connectAppsDomain(domainId, appIds);

        return ResponseDTO.<Integer>builder()
                .data(updated)
                .message("Success")
                .build();
    }

    @DeleteMapping("/domain/{domainId}")
    public ResponseDTO<Integer> disconnectAppDomain(
            @PathVariable(name = "domainId") Long domainId,
            @RequestParam List<Long> appIds
    ) {
        int updated = appService.disconnectAppsDomain(domainId, appIds);

        return ResponseDTO.<Integer>builder()
                .data(updated)
                .message("Success")
                .build();
    }

    @GetMapping()
    public ResponseDTO<List<AppDTO>> getApp(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String code
    ) {
        List<AppDTO> apps;
        if (name == null && code == null) {
            apps = appService.findAll().stream()
                    .map(AppDTO::new)
                    .toList();
        } else {
            apps = appService.findAllByAppNameAndCode(name, code).stream()
                    .map(AppDTO::new)
                    .toList();
        }

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



    //TODO App이랑 팀이랑 연결
    // TODO 도메인이랑 앱이랑 연결

}
