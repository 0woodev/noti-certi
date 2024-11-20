package com.woodev.noticerti.service;


import com.woodev.noticerti.model.App;

import java.util.List;

public interface AppService {
    List<App> findAllByAppNameAndCode(String name, String code);
    App getApp(Long id);
    App save(App newApp);
    App update(App newApp);
    List<App> findAll();

    List<App> findAllByDomainId(Long domainId);

    List<App> findAllByNotDomainId(Long domainId);

    int connectAppsDomain(Long domainId, List<Long> appIds);

    int disconnectAppsDomain(Long domainId, List<Long> appIds);
}
