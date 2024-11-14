package com.woodev.noticerti.service;


import com.woodev.noticerti.dto.CertificateInfoDTO;

import java.net.URL;


public interface CertificateService {
    CertificateInfoDTO getCertificateFromServer(URL url) throws Exception;
}
