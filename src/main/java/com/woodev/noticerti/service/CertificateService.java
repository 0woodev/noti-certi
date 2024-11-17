package com.woodev.noticerti.service;


import com.woodev.noticerti.dto.CertificateInfoDTO;
import com.woodev.noticerti.model.Certificate;

import java.net.URL;
import java.util.Optional;


public interface CertificateService {
    CertificateInfoDTO findCertificateFromServer(URL url) throws Exception;

    Optional<Certificate> findCertificateFromDB(URL url);

    void sync(URL httpsUrl, CertificateInfoDTO certificateFromServer);
}
