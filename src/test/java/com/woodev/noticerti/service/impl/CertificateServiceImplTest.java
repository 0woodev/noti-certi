package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.repository.CertificateRepository;
import com.woodev.noticerti.repository.DomainRepository;
import com.woodev.noticerti.util.URLBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CertificateServiceImplTest {

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private DomainRepository domainRepository;

    @Test
    void findCertificateFromServer() {

        // given
        List<Map<String, Object>> map = List.of(
                Map.of(
                        "host", "www.naver.com",
                        "port", 443
                ),
                Map.of(
                        "host", "campus.hyundai.com",
                        "port", 443
                ),
                Map.of(
                        "host", "campov.hmckmc.co.kr",
                        "port", 443
                ),
                Map.of(
                        "host", "autoway.hyundai.net",
                        "port", 443
                )
        );
        // when
        List<CertificateInfoDTO> certs = new ArrayList<>();

        for (Map<String, Object> m : map) {
            try {
                URL httpsUrl = URLBuilder.getHttps(m.get("host").toString(), (int) m.get("port"));
                CertificateInfoDTO cert = certificateService.findCertificateFromServer(httpsUrl);
                certs.add(cert);
            } catch (Exception e) {
                e.printStackTrace();
                certs.add(null);
            }
        }
        // then
        boolean error = false;
        for (int i = 0; i <certs.size(); i++) {
            CertificateInfoDTO cert = certs.get(i);
            Map<String, Object> d = map.get(i);

            System.out.println("--------------------------------------------------");
            if (cert == null) {
                System.out.println(d.get("host") + ":" + d.get("port") + " 인증서 정보 가져오기 실패");
                error = true;
            } else {
                System.out.println(d.get("host") + ":" + d.get("port") + " 인증서 정보 가져오기 성공");
                System.out.println(cert);
            }
        }

        if (error) {
            fail();
        }
    }

    @Test
    void findCertificateFromDB() {
        try {
            // given
            Certificate cert = Certificate.builder()
                    .id(1L)
                    .validFrom(Instant.now().minusSeconds(86400))
                    .validTo(Instant.now().plusSeconds(31536000))
                    .commonName("www.0woodev.com")
                    .serialNumber("abcdefg")
                    .issuingCA("0woodevCA")
                    .organization("0woodev")
                    .build();

            Domain domain = Domain.builder()
                    .id(1L)
                    .host("www.0woodev.com")
                    .port(443)
                    .certificate(cert)
                    .build();


            // repository 에서 가져오는 것이므로 null 이 아닌 cert 객체를 반환하도록 설정
            when(domainRepository.findByHostAndPort(any(String.class), any(int.class)))
                    .thenReturn(Optional.of(domain));


            URL url = URLBuilder.getHttps("www.0woodev.com", 443);

            // when
            Optional<Certificate> actual = certificateService.findCertificateFromDB(url);

            // then
            assertThat(actual.isPresent()).isTrue();

            Certificate actualCert = actual.get();

            assertThat(actualCert.getId()).isEqualTo(cert.getId());
            assertThat(actualCert.getCommonName()).isEqualTo(cert.getCommonName());
            verify(domainRepository, times(1))
                    .findByHostAndPort(any(String.class), any(int.class));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("Certificate 가 null 인 경우")
    void findCertificateFromDB_returnNull() {
        try {
            // given
            Domain domain = Domain.builder()
                    .id(1L)
                    .host("www.0woodev.com")
                    .port(443)
                    .build();


            // repository 에서 가져오는 것이므로 null 이 아닌 cert 객체를 반환하도록 설정
            when(domainRepository.findByHostAndPort(any(String.class), any(int.class)))
                    .thenReturn(Optional.of(domain));


            URL url = URLBuilder.getHttps("www.0woodev.com", 443);

            // when
            Optional<Certificate> actual = certificateService.findCertificateFromDB(url);

            // then
            assertThat(actual.isEmpty()).isTrue();
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}