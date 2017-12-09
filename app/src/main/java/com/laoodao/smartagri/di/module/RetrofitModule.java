package com.laoodao.smartagri.di.module;

import android.app.Application;

import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.api.service.Api;
import com.laoodao.smartagri.api.support.LoggingInterceptor;
import com.laoodao.smartagri.api.support.RequestHeaderInterceptor;
import com.laoodao.smartagri.utils.DataHelper;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/4/6.
 */

@Module
public class RetrofitModule {
    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) // 失败重发
                .addInterceptor(new RequestHeaderInterceptor())
                .addInterceptor(new LoggingInterceptor());
        return builder.build();
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.APP_BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//使用rxjava
                .addConverterFactory(GsonConverterFactory.create())//使用Gson
                .build();
    }


    /**
     * 提供RXCache客户端
     *
     * @param cacheDirectory RxCache缓存路径
     * @return
     */
    @Singleton
    @Provides
    RxCache provideRxCache(@Named("RxCacheDirectory") File cacheDirectory) {

        return new RxCache
                .Builder()
                .persistence(cacheDirectory, new GsonSpeaker());
    }

    /**
     * 需要单独给RxCache提供缓存路径
     * 提供RxCache缓存地址
     */
    @Provides
    @Named("RxCacheDirectory")
    File provideRxCacheDirectory(Application application) {
        File cacheDirectory = new File(DataHelper.getCacheFile(application), "RxCache");
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }
        return cacheDirectory;
    }


}
