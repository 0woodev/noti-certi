package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.exception.NoticertiException;
import com.woodev.noticerti.model.App;
import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.repository.DomainRepository;
import com.woodev.noticerti.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DomainServiceImpl implements DomainService {
    private final DomainRepository domainRepository;

    @Override
    public Domain getDomain(Long id) {
        return domainRepository.findById(id)
                .orElseThrow(() -> new NoticertiException("도메인이 존재하지 않습니다."));
    }

    @Override
    public Domain save(Domain newDomain) {
        return domainRepository.save(newDomain);
    }

    @Override
    public List<Domain> findAll(String hostName) {
        return domainRepository.findAllByHostNameContaining(hostName);
    }

}
