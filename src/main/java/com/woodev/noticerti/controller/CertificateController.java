package com.woodev.noticerti.controller;

import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.dto.req.URLRequestDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.SubjectAlternativeName;
import com.woodev.noticerti.service.CertificateService;
import com.woodev.noticerti.util.URLBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RequestMapping("/certificate")
@RequiredArgsConstructor
@RestController
public class CertificateController {
    private final CertificateService certificateService;
    private final SubjectAlternativeNameService sanService;
    @PostMapping
    public ResponseEntity<CertificateInfoDTO> addCertificate(@RequestBody URLRequestDTO request) {
        try {
            // HTTPS URL 생성
            URL httpsUrl = URLBuilder.getHttps(request.domain(), request.port());

            // 인증서 정보 가져오기
            Certificate certificate = certificateService.getCertificateFromDB(httpsUrl);

            if (certificate != null) {
                // DB에 이미 저장된 인증서 정보 반환
                List<SubjectAlternativeName> sans = sanService.findAllByCertificate(certificate.getId());

                return ResponseEntity.ok(new CertificateInfoDTO(certificate, sans));
            }

            // DB에 저장된 인증서 정보가 없을 경우
            CertificateInfoDTO certificateInfo = certificateService.getCertificateFromServer(httpsUrl);

            // 성공적으로 인증서 정보 반환
            return ResponseEntity.ok(certificateInfo);

            // TODO Exception 처리
        } catch (IllegalArgumentException e) {
            // 잘못된 URL 형식 등 사용자의 요청 문제 처리
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            // 서버 연결 문제 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (Exception e) {
            // 예상치 못한 서버 문제 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping
    public ResponseEntity<CertificateInfoDTO> getCertificate(
            @RequestParam String domain,
            @RequestParam Integer port
    ) {
        try {
            // HTTPS URL 생성
            URL httpsUrl = URLBuilder.getHttps(domain, port);

            CertificateInfoDTO certificateInfo = certificateService.getCertificateFromServer(httpsUrl);

            // 성공적으로 인증서 정보 반환
            return ResponseEntity.ok(certificateInfo);

            // TODO Exception 처리
        } catch (IllegalArgumentException e) {
            // 잘못된 URL 형식 등 사용자의 요청 문제 처리

            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            // 서버 연결 문제 처리

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (Exception e) {
            // 예상치 못한 서버 문제 처리

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
