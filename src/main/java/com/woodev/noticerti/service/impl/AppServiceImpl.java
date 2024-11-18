package com.woodev.noticerti.service.impl;

import com.woodev.noticerti.exception.NoticertiException;
import com.woodev.noticerti.model.App;
import com.woodev.noticerti.model.Team;
import com.woodev.noticerti.repository.AppRepository;
import com.woodev.noticerti.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppServiceImpl implements AppService {

    private final AppRepository appRepository;

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
    public List<App> findAll(String appName, String code) {
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

    public App getApp(Long id) {
        return appRepository.findById(id)
                .orElseThrow(() -> new NoticertiException("어플리케이션이 존재하지 않습니다."));
    }
}
