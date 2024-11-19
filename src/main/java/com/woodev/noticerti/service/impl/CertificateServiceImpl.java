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
import com.woodev.noticerti.util.DnsResolver;
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
        String ip = DnsResolver.getIpAddressByUrl(httpsUrl.getHost());
        return new CertificateInfoDTO(serverCert, ip);
    }

    /**
     * ServerDomain 테이블에서 host 과 port 를 이용하여 인증서 정보를 가져온다.
     * if 도메인 정보가 없다면 null 을 반환한다.
     *
     * @param url : host, port 정보를 가지고 있는 URL 객체
     * @return Certificate : 인증서 정보
     */
    @Override
    public Optional<Certificate> findCertificateFromDB(URL url) {
        String ip = DnsResolver.getIpAddressByUrl(url.getHost());
        return domainRepository.findByIpAndPort(ip, url.getPort())
                .map(Domain::getCertificate);
    }

    /**
     * 발급기관과 일련번호를 이용하여 인증서 정보를 가져온다.
     * @param issuingCA 발급기관
     * @param serialNumber 일련번호
     * @return Certificate : 인증서 정보
     */
    @Override
    public Optional<Certificate> findCertificateByCAAndSN(String issuingCA, String serialNumber) {
        return certificateRepository.findByIssuingCAAndSerialNumber(issuingCA, serialNumber);
    }


    /**
     * 새로운 인증서 정보를 DB 에 저장한다.
     * 1. Certificate 정보 저장
     * 2. SAN 정보 저장
     * 3. SAN 정보와 Certificate 정보 연관관계 설정
     *
     * @param liveCertificate : 서버에서 가져온 인증서 정보
     * @return Certificate : 저장된 인증서 정보
     */

    @Override
    public Certificate save(CertificateInfoDTO liveCertificate) {
        Certificate certificateFromDB = liveCertificate.toEntity();
        List<SAN> sans = liveCertificate.toSANEntities(certificateFromDB);

        Certificate saved = certificateRepository.save(certificateFromDB);
        sanRepository.saveAll(sans);

        return saved;
    }

}
