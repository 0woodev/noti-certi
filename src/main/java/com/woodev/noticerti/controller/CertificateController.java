package com.woodev.noticerti.controller;

import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.dto.req.URLRequestDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.service.CertificateService;
import com.woodev.noticerti.util.URLBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;

@RequestMapping("/certificate")
@RequiredArgsConstructor
@RestController
public class CertificateController {
    private final CertificateService certificateService;

    @PostMapping
    public ResponseEntity<CertificateInfoDTO> addCertificate(@RequestBody URLRequestDTO request) {
        try {
            // HTTPS URL 생성
            URL httpsUrl = URLBuilder.getHttps(request.domain(), request.port());

            // TODO 인증서 정보 가져오기
            //  Certificate certificate = certificateService.getCertificateFromDB(httpsUrl);
            CertificateInfoDTO certificateInfo = certificateService.getCertificateFromServer(httpsUrl);
            // TODO DB에 저장
            //  certificateService.save(certificateInfo);
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

            // 인증서 정보 가져오기
            // TODO 인증서 정보 가져오기
            //  Certificate certificate = certificateService.getCertificateFromDB(httpsUrl);
            //  if (certificate != null) 인증서 직접 조회하기
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
