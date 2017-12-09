package com.laoodao.smartagri.di.module;

import com.laoodao.smartagri.api.service.DiscoverService;
import com.laoodao.smartagri.api.service.FarmlandService;

import com.laoodao.smartagri.api.service.MarketService;

import com.laoodao.smartagri.api.service.HomeService;

import com.laoodao.smartagri.api.service.QAService;
import com.laoodao.smartagri.api.service.FarmlandService;

import com.laoodao.smartagri.api.service.UserService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/4/11.
 */
@Module
public class ServiceModule {
    @Singleton
    @Provides
    UserService provideUserService(Retrofit retrofit) {
        return retrofit.create(UserService.class);
    }

    @Singleton
    @Provides
    FarmlandService provideFarmlandService(Retrofit retrofit) {
        return retrofit.create(FarmlandService.class);
    }

    @Singleton
    @Provides
    QAService provideQAService(Retrofit retrofit) {
        return retrofit.create(QAService.class);
    }


    @Singleton
    @Provides
    MarketService provideMarketService(Retrofit retrofit) {
        return retrofit.create(MarketService.class);
    }

    @Singleton
    @Provides
    HomeService provideHomeService(Retrofit retrofit) {
        return retrofit.create(HomeService.class);
    }


    @Singleton
    @Provides
    DiscoverService provideDiscoverService(Retrofit retrofit) {
        return retrofit.create(DiscoverService.class);
    }
}
