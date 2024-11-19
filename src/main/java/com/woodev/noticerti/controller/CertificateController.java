package com.woodev.noticerti.controller;

import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.dto.DomainCertConnectDTO;
import com.woodev.noticerti.dto.SimpleDomainDTO;
import com.woodev.noticerti.dto.req.URLRequestDTO;
import com.woodev.noticerti.dto.res.ResponseDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.model.SAN;
import com.woodev.noticerti.service.CertificateService;
import com.woodev.noticerti.service.DomainService;
import com.woodev.noticerti.service.SANService;
import com.woodev.noticerti.util.URLBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequestMapping("/certificate")
@RequiredArgsConstructor
@RestController
public class CertificateController {

    private final DomainService domainService;
    private final CertificateService certificateService;
    private final SANService sanService;
    @PutMapping("/sync")
    public ResponseDTO<DomainCertConnectDTO> connectCertificateAndDomain(@RequestBody URLRequestDTO request) throws Exception {
        Domain domain = domainService.getById(request.domainId());

        URL httpsUrl = URLBuilder.getHttps(domain.getIp(), domain.getPort());
        // 실시간 인증서 정보 가져오기
        CertificateInfoDTO certificateFromServer = certificateService.findCertificateFromServer(httpsUrl);
        Optional<Certificate> certificateOpt = certificateService.findCertificateByCAAndSN(certificateFromServer.getIssuingCA(), certificateFromServer.getSerialNumber());

        Certificate certificateFromDB = null;
        List<SAN> sans;
        if (certificateOpt.isPresent()) {   // 인증서를 관리중 이라면
            certificateFromDB = certificateOpt.get();
            sans = sanService.findAllByCertificate(certificateFromDB.getId());

            // 도메인과 인증서가 잘 연결되어 있는지 확인
            if (domain.getCertificate() == null || !Objects.equals(certificateFromDB.getId(), domain.getCertificate().getId())) {
                domain.setCertificate(certificateFromDB);
                domainService.save(domain);
            }
        } else {    // 인증서가 관리되고 있지 않은 경우
            certificateFromDB = certificateService.save(certificateFromServer);
            sans = sanService.saveAll(certificateFromServer.toSANEntities(certificateFromDB));

            domain.setCertificate(certificateFromDB);
            domainService.save(domain);
        }

        DomainCertConnectDTO connected = new DomainCertConnectDTO(
                new CertificateInfoDTO(certificateFromDB, sans),
                new SimpleDomainDTO(domain)
        );

        return ResponseDTO.<DomainCertConnectDTO>builder()
                .data(connected)
                .message("Success")
                .build();
    }

    /**
     * 인증서 정보 실시간으로 가져오기 (DB 에 저장하지 않음 )
     *
     * @param ip 가져오고자 하는 ip
     * @param port 가져오고자 하는 포트
     * @return 인증서 정보
     */
    @GetMapping("/server")
    public ResponseDTO<CertificateInfoDTO> getCertificateFromServer(
            @RequestParam String ip,
            @RequestParam Integer port
    ) throws Exception {
        // HTTPS URL 생성
        URL httpsUrl = URLBuilder.getHttps(ip, port);
        // 성공적으로 인증서 정보 반환
        return ResponseDTO.<CertificateInfoDTO>builder()
                .data(certificateService.findCertificateFromServer(httpsUrl))
                .message("Success")
                .build();
    }

    /**
     * 인증서 정보 실시간으로 가져오기 (DB 에 저장하지 않음 )
     *
     * @param ip 가져오고자 하는 도메인
     * @param port 가져오고자 하는 포트
     * @return 인증서 정보
     */
    @GetMapping("/db")
    public ResponseDTO<CertificateInfoDTO> getCertificateFromDB(
            @RequestParam String ip,
            @RequestParam Integer port
    ) throws Exception {
        // HTTPS URL 생성
        URL httpsUrl = URLBuilder.getHttps(ip, port);

        // DB 에 저장된 인증서 정보 가져오기
        CertificateInfoDTO liveCert = certificateService.findCertificateFromServer(httpsUrl);
        Optional<Certificate> certificateOpt = certificateService.findCertificateByCAAndSN(liveCert.getIssuingCA(), liveCert.getSerialNumber());

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

    @GetMapping("/{id}")
    public ResponseDTO<CertificateInfoDTO> getCertificateById(@PathVariable(name = "id") Long certificateId) throws Exception {
        Certificate certificate = certificateService.getById(certificateId);
        List<SAN> sans = sanService.findAllByCertificate(certificateId);

        CertificateInfoDTO certificateInfo = new CertificateInfoDTO(certificate, sans);

        // 성공적으로 인증서 정보 반환
        return ResponseDTO.<CertificateInfoDTO>builder()
                .data(certificateInfo)
                .message("Success")
                .build();
    }


    //TODO SAN으로 인증서 검색 / 도메인으로 인증서 검색
    @GetMapping()
    public ResponseDTO<CertificateInfoDTO> getCertificateFromDB(
            @RequestParam String domainName
    ) throws Exception {
        //해당 도메인 이름 검색
        //해당 도메인이 소유한 인증서 정보 가져오기

        //SAN

        // 성공적으로 인증서 정보 반환
        return ResponseDTO.<CertificateInfoDTO>builder()
//                .data(certificateInfo)
                .message("Success")
                .build();
    }
}
