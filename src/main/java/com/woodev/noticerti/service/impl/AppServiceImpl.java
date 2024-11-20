package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.exception.NoticertiException;
import com.woodev.noticerti.model.App;
import com.woodev.noticerti.model.AppDomain;
import com.woodev.noticerti.model.Domain;
import com.woodev.noticerti.repository.AppDomainRepository;
import com.woodev.noticerti.repository.AppRepository;
import com.woodev.noticerti.repository.DomainRepository;
import com.woodev.noticerti.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;
    private final AppDomainRepository appDomainRepository;
    private final DomainRepository domainRepository;
    /**
     * 동일한 이름을 가지는 앱도 존재할 수 있으며, AppName 에 일부를 입력할 수도 있음
     * <pre>
     *  - 1. teamName 만 입력한 경우
     *    - AppName 에서 teamName 을 포함하는 모든 앱을 반환
     *    - ex) AppName = "test" 인 경우, "testApp", "testApp2" 등 모든 앱을 반환
     *  - 2. teamName, code 를 입력한 경우
     *    - AppName 에서 teamName 을 포함하고, Code 가 일치하는 앱을 반환
     *
     * </pre>
     *
     * @param appName 이름
     * @param code 코드
     * @return 검색 결과
     */
    @Override
    public List<App> findAllByAppNameAndCode(String appName, String code) {
        if (code != null) {
            return appRepository.findAllByAppNameContainingAndCode(appName, code);

        } else {
            return appRepository.findAllByAppNameContaining(appName);
        }
    }

    @Override
    public App save(App newApp) {
        return appRepository.save(newApp);
    }

    @Override
    public App update(App newApp) {
        getApp(newApp.getId());
        return appRepository.save(newApp);
    }

    @Override
    public List<App> findAll() {
        return appRepository.findAll();
    }

    public App getApp(Long id) {
        return appRepository.findById(id)
                .orElseThrow(() -> new NoticertiException("어플리케이션이 존재하지 않습니다."));
    }

    @Override
    public List<App> findAllByDomainId(Long domainId) {
        return appDomainRepository.findAllByDomainId(domainId).stream()
                .map(AppDomain::getApp)
                .toList();
    }

    @Override
    public List<App> findAllByNotDomainId(Long domainId) {
        List<App> apps = appRepository.findAll();
        List<App> domainApps = findAllByDomainId(domainId);

        Map<Long, App> domainAppMap = domainApps.stream()
                .collect(Collectors.toMap(App::getId, Function.identity()));

        return apps.stream()
                .filter(app -> !domainAppMap.containsKey(app.getId()))
                .toList();
    }

    @Override
    public int connectAppsDomain(Long domainId, List<Long> appIds) {
        Domain domain = domainRepository.findById(domainId)
                .orElseThrow(() -> new NoticertiException("도메인이 존재하지 않습니다."));

        List<AppDomain> appDomains = appIds.stream()
                .map(appId -> AppDomain.builder()
                        .domain(Domain.builder().id(domainId).build())
                        .app(App.builder().id(appId).build())
                        .build())
                .toList();

        return appDomainRepository.saveAll(appDomains).size();
    }

    @Override
    public int disconnectAppsDomain(Long domainId, List<Long> appIds) {
        List<AppDomain> appDomains = appDomainRepository.findAllByDomainIdAndAppIdIn(domainId, appIds);

        appDomainRepository.deleteAll(appDomains);

        return appDomains.size();
    }
}
