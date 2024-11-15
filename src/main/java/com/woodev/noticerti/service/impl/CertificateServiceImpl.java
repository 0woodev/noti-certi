package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.config.UnsafeSSLContextConfig;
import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.model.Certificate;
import com.woodev.noticerti.repository.CertificateRepository;
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

    @Override
    public Certificate getCertificateFromDB(URL url) {
        return null;
    }
}
