package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.config.UnsafeSSLContextConfig;
import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.ServiceDomain;
import com.woodev.noticerti.repository.CertificateRepository;
import com.woodev.noticerti.repository.ServiceDomainRepository;
import com.woodev.noticerti.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.security.cert.X509Certificate;


@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    private final ServiceDomainRepository serviceDomainRepository;

    @Override
    public CertificateInfoDTO getCertificateFromServer(URL httpsUrl) throws IOException {
        HttpsURLConnection conn = UnsafeSSLContextConfig.getUnsafeHttpsURLConnection(httpsUrl);
        conn.setConnectTimeout(3000);
        conn.connect();

        X509Certificate[] certs = (X509Certificate[]) conn.getServerCertificates();
        X509Certificate serverCert = certs[0];

        conn.disconnect();

        return new CertificateInfoDTO(serverCert);
    }

    /**
     * ServerDomain 테이블에서 domain 과 port 를 이용하여 인증서 정보를 가져온다.
     * if 도메인 정보가 없다면 null 을 반환한다.
     *
     * @param url
     * @return
     */
    @Override
    public Certificate getCertificateFromDB(URL url) {
        String domain = url.getHost();
        int port = url.getPort();

        return serviceDomainRepository.findByDomainAndPort(domain, port)
                .map(ServiceDomain::getCertificate)
                .orElse(null);
    }
}
