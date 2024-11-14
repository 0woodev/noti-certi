package com.woodev.noticerti.util;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class URLBuilderTest {

    @Test
    void getHttps() {
        try {
            URL https = URLBuilder.getHttps("www.google.com", 443);

            assertThat(https.getProtocol()).isEqualTo("https");
            assertThat(https.getHost()).isEqualTo("www.google.com");
            assertThat(https.getPort()).isEqualTo(443);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    void getHttpsWithHttps() {
        try {
            URL https = URLBuilder.getHttps("https://www.google.com", 443);

            assertThat(https.getProtocol()).isEqualTo("https");
            assertThat(https.getHost()).isEqualTo("www.google.com");
            assertThat(https.getPort()).isEqualTo(443);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    void getHttp() {
        try {
            URL http = URLBuilder.getHttp("www.google.com", 80);

            assertThat(http.getProtocol()).isEqualTo("http");
            assertThat(http.getHost()).isEqualTo("www.google.com");
            assertThat(http.getPort()).isEqualTo(80);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    void getHttpWithHttp() {
        try {
            URL http = URLBuilder.getHttp("http://www.google.com", 80);

            assertThat(http.getProtocol()).isEqualTo("http");
            assertThat(http.getHost()).isEqualTo("www.google.com");
            assertThat(http.getPort()).isEqualTo(80);
        } catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }

}