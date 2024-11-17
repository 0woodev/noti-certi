package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.config.UnsafeSSLContextConfig;
import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.model.SAN;
import com.woodev.noticerti.repository.CertificateRepository;
import com.woodev.noticerti.repository.DomainRepository;
import com.woodev.noticerti.repository.SANRepository;
import com.woodev.noticerti.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    private final DomainRepository domainRepository;

    private final SANRepository sanRepository;

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


    /**
     * DB 에 저장된 인증서 정보와 서버에서 가져온 인증서 정보를 비교하여 변경사항에 따라 DB 에 반영한다.
     * 1. DB 에 인증서 정보가 없는 경우
     * - 인증서 정보와 SAN 정보를 DB 에 저장
     * - Domain 정보와 Certificate 정보를 연관관계로 설정
     * 2. DB 에 인증서 정보가 있는 경우
     * - 서버에서 가져온 인증서와 DB 에 저장된 인증서를 비교
     * - 새로운 인증서가 발급 되었다면 DB 에 반영
     *
     * @param httpsUrl              : 도메인과 포트 정보를 가지고 있는 URL 객체
     * @param certificateFromServer : 서버에서 가져온 인증서 정보
     * @return Certificate : 현 시점 기준 인증서 정보
     */
    @Override
    public Certificate sync(URL httpsUrl, CertificateInfoDTO certificateFromServer) {
        Optional<Domain> domainOpt = domainRepository.findByHostAndPort(httpsUrl.getHost(), httpsUrl.getPort());

        Certificate certificateFromDB = null;
        if (domainOpt.isPresent()) { // DB 에 인증서 정보가 저장되어 있는 경우
            Domain domain = domainOpt.get();
            certificateFromDB = domain.getCertificate();

            String liveCertKey = certificateFromServer.getPrimaryKey();
            String storedCertKey = certificateFromDB.getPrimaryKey();

            // 새로운 인증서로 업데이트가 되었다면 DB에 반영
            // 1. 새로운 인증서와 SAN 데이터 추가
            // 2. Domain 과 Certificate, SAN 연관관계 설정
            boolean isNewCertificate = !storedCertKey.equals(liveCertKey);
            if (isNewCertificate) {
                certificateFromDB = this.saveNewCertificate(certificateFromServer, httpsUrl);
            }

        } else { // DB 에 인증서 정보가 없는 경우
            certificateFromDB = this.saveNewCertificate(certificateFromServer, httpsUrl);
        }

        return certificateFromDB;
    }

    /**
     * 새로운 인증서 정보를 DB 에 저장한다.
     * 1. Certificate 정보 저장
     * 2. SAN 정보 저장
     * 3. SAN 정보와 Certificate 정보 연관관계 설정
     * 4. Domain 정보 저장
     * 5. Domain 정보와 Certificate 정보 연관관계 설정
     *
     * @param certificateFromServer : 서버에서 가져온 인증서 정보
     * @param httpsUrl : 도메인과 포트 정보를 가지고 있는 URL 객체
     * @return Certificate : 저장된 인증서 정보
     */
    private Certificate saveNewCertificate(CertificateInfoDTO certificateFromServer, URL httpsUrl) {
        Certificate certificateFromDB = certificateFromServer.toEntity();
        List<SAN> sans = certificateFromServer.toSANEntities(certificateFromDB);

        certificateRepository.save(certificateFromDB);
        sanRepository.saveAll(sans);

        domainRepository.save(Domain.builder()
                .host(httpsUrl.getHost())
                .port(httpsUrl.getPort())
                .certificate(certificateFromDB)
                .build());

        return certificateFromDB;
    }
}
