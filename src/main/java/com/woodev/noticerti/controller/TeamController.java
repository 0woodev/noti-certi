package com.woodev.noticerti.controller;

import com.woodev.noticerti.dto.SimpleTeamDTO;
import com.woodev.noticerti.dto.req.TeamCreationRequestDTO;
import com.woodev.noticerti.dto.req.TeamUpdateRequestDTO;
import com.woodev.noticerti.dto.res.ResponseDTO;
import com.woodev.noticerti.dto.TeamDTO;
import com.woodev.noticerti.model.Team;
import com.woodev.noticerti.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/team")
@RequiredArgsConstructor
@RestController
public class TeamController {
    private final TeamService teamService;

    @PostMapping
    public ResponseDTO<TeamDTO> createTeam(
        @RequestBody TeamCreationRequestDTO request
    ) {
        Team team =  teamService.save(request.toEntity());

        return ResponseDTO.<TeamDTO>builder()
                .data(new TeamDTO(team))
                .message("Success")
                .build();
    }

    @PutMapping
    public ResponseDTO<TeamDTO> updateTeam(
            @RequestBody TeamUpdateRequestDTO request
    ) {
        Team team =  teamService.update(request.toEntity());

        return ResponseDTO.<TeamDTO>builder()
                .data(new TeamDTO(team))
                .message("Success")
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<TeamDTO> getTeam(
        @PathVariable Long id
    ) {
        Team team = teamService.getTeam(id);

        return ResponseDTO.<TeamDTO>builder()
                .data(new TeamDTO(team))
                .message("Success")
                .build();
    }

    @GetMapping()
    public ResponseDTO<List<SimpleTeamDTO>> getTeam(
        @RequestParam(defaultValue = "") String teamName
    ) {
        List<SimpleTeamDTO> teams = teamService.findAllByNameContaining(teamName).stream()
                .map(SimpleTeamDTO::new)
                .toList();

        return ResponseDTO.<List<SimpleTeamDTO>>builder()
                .data(teams)
                .message("Success")
                .build();
    }
}
