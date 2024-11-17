package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.config.UnsafeSSLContextConfig;
import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.repository.CertificateRepository;
import com.woodev.noticerti.repository.DomainRepository;
import com.woodev.noticerti.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    private final DomainRepository domainRepository;

    @Override
    public CertificateInfoDTO findCertificateFromServer(URL httpsUrl) throws IOException {
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
     * @param url : domain, port 정보를 가지고 있는 URL 객체
     * @return Certificate : 인증서 정보
     */
    @Override
    public Optional<Certificate> findCertificateFromDB(URL url) {
        String domain = url.getHost();
        int port = url.getPort();

        return domainRepository.findByHostAndPort(domain, port)
                .map(Domain::getCertificate);
    }

    @Override
    public void sync(URL httpsUrl, CertificateInfoDTO certificateFromServer) {

    }
}
