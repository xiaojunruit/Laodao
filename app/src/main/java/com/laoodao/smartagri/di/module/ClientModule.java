package com.laoodao.smartagri.di.module;

import com.laoodao.smartagri.base.AppManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ouyuan on 2017/4/17.
 */
public class ClientModule {


    private AppManager mAppManager;

    public ClientModule(AppManager appManager) {
        this.mAppManager = appManager;
    }


    public AppManager provideAppManager(){
        return mAppManager;
    }

}
