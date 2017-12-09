package com.laoodao.smartagri.di.module;

import com.laoodao.smartagri.api.cache.HomeCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache.internal.RxCache;
import retrofit2.Retrofit;

/**
 * Created by 欧源 on 2017/5/16.
 */
@Module
public class CacheModule {

    @Singleton
    @Provides
    HomeCache provideUserService(RxCache rxCache) {
        return rxCache.using(HomeCache.class);
    }
}
