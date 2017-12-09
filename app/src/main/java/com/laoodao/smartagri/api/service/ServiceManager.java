package com.laoodao.smartagri.api.service;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Administrator on 2017/4/11.
 */
@Singleton
public class ServiceManager {


    private UserService mUserService;

    private FarmlandService mFarmlandService;

    private QAService mQAService;
    private MarketService mMarketService;

    private HomeService mHomeService;

    private DiscoverService mDiscoverService;

    @Inject
    public ServiceManager(UserService userService, FarmlandService farmlandService, QAService qaService, HomeService homeService, MarketService marketService, DiscoverService discoverService) {

        this.mUserService = userService;
        this.mFarmlandService = farmlandService;
        this.mQAService = qaService;
        this.mMarketService = marketService;
        this.mHomeService = homeService;
        this.mDiscoverService = discoverService;
    }

    public UserService getUserService() {
        return mUserService;
    }

    public FarmlandService getFarmlandService() {
        return mFarmlandService;
    }

    public QAService getQAService() {
        return mQAService;
    }


    public MarketService getMarketService() {
        return mMarketService;
    }

    public HomeService getHomeService() {
        return mHomeService;
    }


    public DiscoverService getDiscoverService() {
        return mDiscoverService;
    }


}
