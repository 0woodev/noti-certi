package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.ServiceDomain;
import com.woodev.noticerti.repository.CertificateRepository;
import com.woodev.noticerti.repository.ServiceDomainRepository;
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
    private ServiceDomainRepository serviceDomainRepository;

    @Test
    void getCertificateFromServer() {

        // given
        List<Map<String, Object>> map = List.of(
                Map.of(
                        "domain", "www.naver.com",
                        "port", 443
                ),
                Map.of(
                        "domain", "campus.hyundai.com",
                        "port", 443
                ),
                Map.of(
                        "domain", "campov.hmckmc.co.kr",
                        "port", 443
                ),
                Map.of(
                        "domain", "autoway.hyundai.net",
                        "port", 443
                )
        );
        // when
        List<CertificateInfoDTO> certs = new ArrayList<>();

        for (Map<String, Object> m : map) {
            try {
                URL httpsUrl = URLBuilder.getHttps(m.get("domain").toString(), (int) m.get("port"));
                CertificateInfoDTO cert = certificateService.getCertificateFromServer(httpsUrl);
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
                System.out.println(d.get("domain") + ":" + d.get("port") + " 인증서 정보 가져오기 실패");
                error = true;
            } else {
                System.out.println(d.get("domain") + ":" + d.get("port") + " 인증서 정보 가져오기 성공");
                System.out.println(cert);
            }
        }

        if (error) {
            fail();
        }
    }

    @Test
    void getCertificateFromDB() {
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

            ServiceDomain domain = ServiceDomain.builder()
                    .id(1L)
                    .domain("www.0woodev.com")
                    .port(443)
                    .certificate(cert)
                    .build();


            // repository 에서 가져오는 것이므로 null 이 아닌 cert 객체를 반환하도록 설정
            when(serviceDomainRepository.findByDomainAndPort(any(String.class), any(int.class)))
                    .thenReturn(Optional.of(domain));


            URL url = URLBuilder.getHttps("www.0woodev.com", 443);

            // when
            Certificate actual = certificateService.getCertificateFromDB(url);

            // then
            assertThat(actual).isNotNull();
            assertThat(actual.getId()).isEqualTo(cert.getId());
            assertThat(actual.getCommonName()).isEqualTo(cert.getCommonName());
            verify(serviceDomainRepository, times(1))
                    .findByDomainAndPort(any(String.class), any(int.class));

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("Certificate 가 null 인 경우")
    void getCertificateFromDB_returnNull() {
        try {
            // given


            ServiceDomain domain = ServiceDomain.builder()
                    .id(1L)
                    .domain("www.0woodev.com")
                    .port(443)
                    .build();


            // repository 에서 가져오는 것이므로 null 이 아닌 cert 객체를 반환하도록 설정
            when(serviceDomainRepository.findByDomainAndPort(any(String.class), any(int.class)))
                    .thenReturn(Optional.of(domain));


            URL url = URLBuilder.getHttps("www.0woodev.com", 443);

            // when
            Certificate actual = certificateService.getCertificateFromDB(url);

            // then
            assertThat(actual).isNull();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}