package com.laoodao.smartagri.api.cache;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by 欧源 on 2017/5/16.
 */
@Singleton
public class CacheManager {

    private HomeCache mHomeCache;
    @Inject
    public CacheManager(HomeCache userService){
        this.mHomeCache = userService;
    }


    public HomeCache getHomeCache(){

        return mHomeCache;
    }
}
