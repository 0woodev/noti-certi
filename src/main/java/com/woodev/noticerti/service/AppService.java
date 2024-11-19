package com.woodev.noticerti.service;


import com.woodev.noticerti.model.App;

import java.util.Collection;
import java.util.List;

public interface AppService {
    List<App> findAllByAppNameAndCode(String name, String code);
    App getApp(Long id);
    App save(App newApp);
    App update(App newApp);
    List<App> findAll();
}
