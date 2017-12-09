package com.laoodao.smartagri.di.component;

import android.app.Application;

import com.google.gson.Gson;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.api.cache.CacheManager;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.di.module.AppModule;
import com.laoodao.smartagri.di.module.CacheModule;
import com.laoodao.smartagri.di.module.RetrofitModule;
import com.laoodao.smartagri.di.module.ServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Administrator on 2017/4/11.
 */
@Singleton
@Component(modules = {AppModule.class, RetrofitModule.class, CacheModule.class, ServiceModule.class})
public interface AppComponent {

    void inject(LDApplication application);

    Application getApplication();

    Gson getGson();

    ServiceManager getServiceManager();

    //缓存管理器
    CacheManager cacheManager();
}
