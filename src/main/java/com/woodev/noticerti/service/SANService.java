package com.woodev.noticerti.service;


import com.woodev.noticerti.model.SAN;

import java.util.List;

public interface SANService {
    List<SAN> findAllByCertificate(Long certificateId);
}
