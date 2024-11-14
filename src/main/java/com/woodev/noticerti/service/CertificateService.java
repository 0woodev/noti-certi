package com.woodev.noticerti.service;


import com.woodev.noticerti.dto.CertificateInfoDTO;


public interface CertificateService {
    CertificateInfoDTO getCertificateFromServer(String url) throws Exception;
}
