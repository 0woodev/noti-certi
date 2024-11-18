package com.woodev.noticerti.service;


import com.woodev.noticerti.model.App;
import com.woodev.noticerti.model.AppDomain;
import com.woodev.noticerti.model.Team;

import java.util.List;

public interface AppService {
    List<App> findAll(String name, String code);
    App getApp(Long id);
    App save(App newApp);
    App update(App newApp);
    App saveWithTeam(Long appId, Team team);

    void saveAppDomain(AppDomain appDomain);
}
