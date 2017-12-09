package com.laoodao.smartagri.api.cache;

import com.laoodao.smartagri.bean.Home;
import com.laoodao.smartagri.bean.News;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Result;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.rx_cache.DynamicKey;
import io.rx_cache.EvictProvider;
import io.rx_cache.LifeCache;
import io.rx_cache.Reply;
import rx.Observable;

/**
 * Created by 欧源 on 2017/5/16.
 */

public interface HomeCache {


    Observable<Reply<Result<Home>>> getHomeMenu(Observable<Result<Home>> home, EvictProvider evictProvider);

    Observable<Reply<Page<News>>> homeNees(Observable<Page<News>> homenews, EvictProvider evictProvider);
}
