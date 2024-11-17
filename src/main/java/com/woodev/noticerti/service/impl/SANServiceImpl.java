package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.model.SAN;
import com.woodev.noticerti.repository.SANRepository;
import com.woodev.noticerti.service.SANService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SANServiceImpl implements SANService {

    private final SANRepository sanRepository;
    @Override
    public List<SAN> findAllByCertificate(Long certificateId) {
        return sanRepository.findAllByCertificateId(certificateId);
    }
}
