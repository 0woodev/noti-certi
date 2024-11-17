package com.woodev.noticerti.controller;

import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.dto.req.URLRequestDTO;
import com.woodev.noticerti.dto.res.ResponseDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.SAN;
import com.woodev.noticerti.service.CertificateService;
import com.woodev.noticerti.service.SANService;
import com.woodev.noticerti.util.URLBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@RequestMapping("/certificate")
@RequiredArgsConstructor
@RestController
public class CertificateController {

    private final CertificateService certificateService;
    private final SANService sanService;

    @PutMapping("/sync")
    public ResponseDTO<CertificateInfoDTO> createOrUpdateCertificate(@RequestBody URLRequestDTO request) throws Exception {
        // HTTPS URL 생성
        URL httpsUrl = URLBuilder.getHttps(request.domain(), request.port());

        // 인증서 정보 가져오기
        CertificateInfoDTO certificateFromServer = certificateService.findCertificateFromServer(httpsUrl);

        // 인증서 정보 DB 에 반영
        Certificate certificate = certificateService.sync(httpsUrl, certificateFromServer);
        certificateFromServer.setId(certificate.getId());

        return ResponseDTO.<CertificateInfoDTO>builder()
                .data(certificateFromServer)
                .message("Success")
                .build();
    }

    /**
     * 인증서 정보 실시간으로 가져오기 (DB 에 저장하지 않음 )
     *
     * @param domain 가져오고자 하는 도메인
     * @param port 가져오고자 하는 포트
     * @return 인증서 정보
     */
    @GetMapping("/server")
    public ResponseDTO<CertificateInfoDTO> getCertificateFromServer(
            @RequestParam String domain,
            @RequestParam Integer port
    ) throws Exception {
        // HTTPS URL 생성
        URL httpsUrl = URLBuilder.getHttps(domain, port);
        // 성공적으로 인증서 정보 반환
        return ResponseDTO.<CertificateInfoDTO>builder()
                .data(certificateService.findCertificateFromServer(httpsUrl))
                .message("Success")
                .build();
    }

    /**
     * 인증서 정보 실시간으로 가져오기 (DB 에 저장하지 않음 )
     *
     * @param domain 가져오고자 하는 도메인
     * @param port 가져오고자 하는 포트
     * @return 인증서 정보
     */
    @GetMapping("/db")
    public ResponseDTO<CertificateInfoDTO> getCertificateFromDB(
            @RequestParam String domain,
            @RequestParam Integer port
    ) throws Exception {
        // HTTPS URL 생성
        URL httpsUrl = URLBuilder.getHttps(domain, port);

        // DB 에 저장된 인증서 정보 가져오기
        Optional<Certificate> certificateOpt = certificateService.findCertificateFromDB(httpsUrl);

        CertificateInfoDTO certificateInfo = null;
        if (certificateOpt.isPresent()) {
            Certificate certificate = certificateOpt.get();
            List<SAN> sans = sanService.findAllByCertificate(certificate.getId());
            certificateInfo = new CertificateInfoDTO(certificate, sans);
        }

        // 성공적으로 인증서 정보 반환
        return ResponseDTO.<CertificateInfoDTO>builder()
                .data(certificateInfo)
                .message("Success")
                .build();
    }
}
