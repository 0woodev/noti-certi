package com.woodev.noticerti.controller;


import com.woodev.noticerti.dto.DomainDTO;
import com.woodev.noticerti.dto.SimpleDomainDTO;
import com.woodev.noticerti.dto.req.DomainCreationRequestDTO;
import com.woodev.noticerti.dto.res.ResponseDTO;
import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RequestMapping("/domain")
@RequiredArgsConstructor
@RestController
public class DomainController {
    private final DomainService domainService;

    // Read
    @GetMapping("/{id}")
    public ResponseDTO<SimpleDomainDTO> getDomain(@PathVariable(name = "id") Long id) {
        Domain domain = domainService.getById(id);

        return ResponseDTO.<SimpleDomainDTO>builder()
                .data(new SimpleDomainDTO(domain))
                .message("Success")
                .build();
    }

    @GetMapping("/ip")
    public ResponseDTO<String> getDomain(
            @RequestParam String host,
            @RequestParam(defaultValue = "443") int port
    ) throws MalformedURLException {
        String ip = domainService.getIpFromDNS(host, port);

        return ResponseDTO.<String>builder()
                .data(ip)
                .message("Success")
                .build();
    }

    @PutMapping
    public ResponseDTO<DomainDTO> createDomain(
            @RequestBody DomainCreationRequestDTO request
    ) {
        Domain domain = domainService.save(request.toEntity());
        return ResponseDTO.<DomainDTO>builder()
                .data(new DomainDTO(domain))
                .message("Success")
                .build();
    }
}