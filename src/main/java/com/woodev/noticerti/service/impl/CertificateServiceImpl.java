package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.config.UnsafeSSLContextConfig;
import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.service.CertificateService;
import com.woodev.noticerti.util.URLBuilder;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;


@Service
public class CertificateServiceImpl implements CertificateService {
    @Override
    public CertificateInfoDTO getCertificateFromServer(URL httpsUrl) throws IOException {
        HttpsURLConnection conn = UnsafeSSLContextConfig.getUnsafeHttpsURLConnection(httpsUrl);
        conn.setConnectTimeout(3000);
        conn.connect();

        Certificate[] certs = conn.getServerCertificates();
        X509Certificate serverCert = (X509Certificate) certs[0];

        conn.disconnect();

        return new CertificateInfoDTO(serverCert);
    }
}
