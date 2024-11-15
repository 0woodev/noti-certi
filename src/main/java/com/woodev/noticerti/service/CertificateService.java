package com.woodev.noticerti.service;


import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.model.Certificate;

import java.net.URL;


public interface CertificateService {
    CertificateInfoDTO getCertificateFromServer(URL url) throws Exception;

    Certificate getCertificateFromDB(URL url);
}
