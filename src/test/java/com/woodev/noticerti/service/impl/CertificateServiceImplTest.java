package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.service.CertificateService;
import com.woodev.noticerti.util.URLBuilder;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CertificateServiceImplTest {
    private final CertificateService certificateService = new CertificateServiceImpl();

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
        // given

        // when

        // then

    }


}