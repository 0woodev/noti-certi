package com.woodev.noticerti.controller;


import com.woodev.noticerti.dto.AppDTO;
import com.woodev.noticerti.dto.req.AppCreationRequestDTO;
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

    // Read
    @GetMapping()
    public ResponseDTO<List<AppDTO>> getApp(
            @RequestParam String name,
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

    @PutMapping
    public ResponseDTO<AppDTO> createApp(@RequestBody AppCreationRequestDTO request) {
        Team team = request.teamId() == null ? null : teamService.getTeam(request.teamId());

        App app = appService.save(request.toEntity(team));
        return ResponseDTO.<AppDTO>builder()
                .data(new AppDTO(app))
                .message("Success")
                .build();
    }

}
