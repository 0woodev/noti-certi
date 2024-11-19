package com.woodev.noticerti.util;

import java.net.MalformedURLException;
import java.net.URL;

import static org.springframework.util.ResourceUtils.toURL;

public class URLBuilder {

    public static final String HTTPS = "https://";
    public static final String HTTP = "http://";

    public static URL getHttps(String host, int port) throws MalformedURLException {
        if (!host.startsWith(HTTPS)) {
            host = HTTPS + host;
        }

        return toURL(host + ":" + port);
    }

    public static URL getHttp(String host, int port) throws MalformedURLException {
        if (!host.startsWith(HTTP)) {
            host = HTTP + host;
        }

        return toURL(host + ":" + port);
    }
}
