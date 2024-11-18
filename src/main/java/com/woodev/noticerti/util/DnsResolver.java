package com.woodev.noticerti.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class DnsResolver  {

    public static String getIpAddressByUrl(String url) {
        try {
            InetAddress inetAddress = InetAddress.getByName(url);
            return inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
