package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.exception.NoticertiException;
import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.repository.DomainRepository;
import com.woodev.noticerti.service.DomainService;
import com.woodev.noticerti.util.DnsResolver;
import com.woodev.noticerti.util.URLBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DomainServiceImpl implements DomainService {

    private final DomainRepository domainRepository;

    @Override
    public Optional<Domain> findByIpAndPort(String ip, int port) {
        return domainRepository.findByIpAndPort(ip, port);
    }

    @Override
    public Domain getByIpAndPort(String ip, int port) {
        return domainRepository.findByIpAndPort(ip, port)
                .orElseThrow(() -> new NoticertiException("해당 도메인이 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }

    @Override
    public Domain getById(Long id) {
        return domainRepository.findById(id)
                .orElseThrow(() -> new NoticertiException("해당 도메인이 존재하지 않습니다.", HttpStatus.NOT_FOUND));
    }

    @Override
    public String getIpFromDNS(String host, int port) throws MalformedURLException {
        URL httpsUrl = URLBuilder.getHttps(host, port);
        return DnsResolver.getIpAddressByUrl(httpsUrl.getHost());
    }

    /**
     * 도메인 저장
     * @param entity 도메인
     * @return 저장된 도메인
     */
    @Override
    public Domain save(Domain entity) {
        if (domainRepository.existsByIpAndPort(entity.getIp(), entity.getPort())) {
            throw new NoticertiException("이미 등록된 도메인입니다.", HttpStatus.BAD_REQUEST);
        }

        return domainRepository.save(entity);
    }

    // TODO CREATE DOMAIN
}
