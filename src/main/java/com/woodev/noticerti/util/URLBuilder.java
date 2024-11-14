package com.woodev.noticerti.util;

import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.util.ResourceUtils.toURL;

public class URLBuilder {

    public static final String HTTPS = "https://";
    public static final String HTTP = "http://";

    public static URL getHttps(String domain, int port) throws MalformedURLException {
        if (!domain.startsWith(HTTPS)) {
            domain = HTTPS + domain;
        }

        return toURL(domain + ":" + port);
    }

    public static URL getHttp(String domain, int port) throws MalformedURLException {
        if (!domain.startsWith(HTTP)) {
            domain = HTTP + domain;
        }

        return toURL(domain + ":" + port);
    }
}
