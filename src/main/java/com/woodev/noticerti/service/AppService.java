package com.woodev.noticerti.service;


import com.woodev.noticerti.model.App;

import java.util.List;

public interface AppService {
    List<App> findAll(String name, String code);
    App getApp(Long id);
    App save(App newApp);
}
