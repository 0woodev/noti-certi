package com.woodev.noticerti.config;

import javax.net.ssl.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


/**
 * UnsafeSSLContextConfig
 * Https 요청에서 인증서 검증을 하지 않는 HttpsURLConnection을 반환한다.
 */
public class UnsafeSSLContextConfig {

    /**
     * 인증서 검증을 하지 않는 SSLContext 를 생성한다.
     *
     * @return 인증서 검증을 하지 않는 SSLContext
     */
    private static SSLContext createUnsafeSSLContext() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(
                    new KeyManager[0],
                    new TrustManager[] {new UnsafeTrustManager()},
                    new SecureRandom()
            );

            return ctx;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Https 요청에서 인증서 검증을 하지 않는 HttpsURLConnection을 반환한다.
     * 인증서 검증을 하지 않는 것은 보안에 취약할 수 있으므로 주의해야 한다.
     * 따라서, 실제 서비스 에서는 사용하지 않는 것을 권장한다.
     * <p>
     * 하지만,
     * 도메인 들의 인증서 들을 모두 알 수 없으므로,
     * 또 단순히 인증서의 만료 기한만 하면 되므로 보안적인 위험도가 적다고 판단한다.
     *
     * @param url Https 요청을 보낼 URL
     * @return 인증서 검증을 하지 않는 HttpsURLConnection
     */
    public static HttpsURLConnection getUnsafeHttpsURLConnection(URL url) {
        try {
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(createUnsafeSSLContext().getSocketFactory());
            conn.setHostnameVerifier((hostname, session) -> true);

            return conn;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create HTTPS connection", e);
        }
    }

    private static class UnsafeTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
