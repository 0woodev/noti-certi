package com.woodev.noticerti.controller;

import com.woodev.noticerti.dto.AppDTO;
import com.woodev.noticerti.dto.res.ResponseDTO;
import com.woodev.noticerti.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DomainController {

    private final DomainService domainService;

    @GetMapping()
    public ResponseDTO<List<AppDTO>> getDomain(
            @RequestParam(defaultValue = "") String hostName
    ) {

        domainService.findAll(hostName).stream()

//        List<AppDTO> apps = appService.findAll(name, code).stream()
//                .map(AppDTO::new)
//                .toList();

        return ResponseDTO.<List<AppDTO>>builder()
                .data(apps)
                .message("Success")
                .build();
    }
}
