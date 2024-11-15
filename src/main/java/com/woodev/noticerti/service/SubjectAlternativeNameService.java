package com.woodev.noticerti.service;


import com.woodev.noticerti.model.SubjectAlternativeName;

import java.util.List;

public interface SubjectAlternativeNameService {
    List<SubjectAlternativeName> findAllByCertificate(Long certificateId);
}
