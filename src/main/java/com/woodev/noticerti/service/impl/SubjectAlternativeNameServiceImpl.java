package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.model.SubjectAlternativeName;
import com.woodev.noticerti.repository.SubjectAlternativeNameRepository;
import com.woodev.noticerti.service.SubjectAlternativeNameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SubjectAlternativeNameServiceImpl implements SubjectAlternativeNameService {

    private final SubjectAlternativeNameRepository sanRepository;
    @Override
    public List<SubjectAlternativeName> findAllByCertificate(Long certificateId) {
        return sanRepository.findAllByCertificateId(certificateId);
    }
}
