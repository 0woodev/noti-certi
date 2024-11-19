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
import java.util.List;

@RequestMapping("/domain")
@RequiredArgsConstructor
@RestController
public class DomainController {
    private final DomainService domainService;

    // Read
    @GetMapping
    public ResponseDTO<DomainDTO> findDomainByIpAndPort(
            @RequestParam String ip,
            @RequestParam(defaultValue = "443") int port
    ) {
        DomainDTO domain = domainService.findByIpAndPort(ip, port)
                .map(DomainDTO::new)
                .orElse(null);

        return ResponseDTO.<DomainDTO>builder()
                .data(domain)
                .message("Success")
                .build();
    }

    @GetMapping("/{id}")
    public ResponseDTO<DomainDTO> getDomainById(@PathVariable(name = "id") Long id) {
        Domain domain = domainService.getById(id);

        return ResponseDTO.<DomainDTO>builder()
                .data(new DomainDTO(domain))
                .message("Success")
                .build();
    }

    @GetMapping("/ip")
    public ResponseDTO<String> getDomainIp(
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

    @GetMapping("/search")
    public ResponseDTO<List<DomainDTO>> findDomains(@RequestParam(required = false) String host) {
        List<DomainDTO> domains;
        if (host == null) {
            domains = domainService.findAll().stream()
                    .map(DomainDTO::new)
                    .toList();
        } else {
            domains = domainService.findAllByHostContaining(host).stream()
                    .map(DomainDTO::new)
                    .toList();
        }

        return ResponseDTO.<List<DomainDTO>>builder()
                .data(domains)
                .message("Success")
                .build();
    }

    @GetMapping("/app/{id}")
    public ResponseDTO<List<DomainDTO>> getDomainsByAppId(@PathVariable(name = "id") Long appId) {
        List<DomainDTO> domains = domainService.findAllByAppId(appId).stream()
                .map(DomainDTO::new)
                .toList();

        return ResponseDTO.<List<DomainDTO>>builder()
                .data(domains)
                .message("Success")
                .build();
    }
}