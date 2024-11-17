package com.woodev.noticerti.service;


import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.model.Certificate;

import java.net.URL;
import java.util.Optional;


public interface CertificateService {
    CertificateInfoDTO findCertificateFromServer(URL url) throws Exception;

    Certificate getCertificateFromDB(URL url);
    Optional<Certificate> findCertificateFromDB(URL url);
}
