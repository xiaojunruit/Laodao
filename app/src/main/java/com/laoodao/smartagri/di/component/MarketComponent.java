package com.laoodao.smartagri.di.component;

import com.laoodao.smartagri.di.scope.ActivityScope;
import com.laoodao.smartagri.ui.market.activity.BuyDetailsActivity;
import com.laoodao.smartagri.ui.market.activity.MarketSearchActivity;
import com.laoodao.smartagri.ui.market.activity.MyBrowseActivity;
import com.laoodao.smartagri.ui.market.activity.MyCollectionActivity;
import com.laoodao.smartagri.ui.market.activity.MyReleaseActivity;
import com.laoodao.smartagri.ui.market.activity.ReleaseBuyingActivity;
import com.laoodao.smartagri.ui.market.activity.ReleaseSupplyingActivity;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.ui.market.fragment.BrowseBuyFragment;
import com.laoodao.smartagri.ui.market.fragment.BrowseSalesFragment;
import com.laoodao.smartagri.ui.market.fragment.BuyFragment;
import com.laoodao.smartagri.ui.market.fragment.CollectionBuyFragment;
import com.laoodao.smartagri.ui.market.fragment.CollectionSalesFragment;
import com.laoodao.smartagri.ui.market.fragment.MarketFragment;
import com.laoodao.smartagri.ui.market.fragment.MySupplyDemandFragment;
import com.laoodao.smartagri.ui.market.fragment.ReleaseBuyFragment;
import com.laoodao.smartagri.ui.market.fragment.ReleaseSupplyFragment;
import com.laoodao.smartagri.ui.market.fragment.SalesFragment;

import dagger.Component;

/**
 * Created by 欧源 on 2017/4/18.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface MarketComponent {
    MarketFragment inject(MarketFragment fragment);

    BuyFragment inject(BuyFragment fragment);

    SalesFragment inject(SalesFragment fragment);

    MySupplyDemandFragment inject(MySupplyDemandFragment fragment);

    MyReleaseActivity inject(MyReleaseActivity activity);

    ReleaseBuyFragment inject(ReleaseBuyFragment fragment);

    ReleaseSupplyFragment inject(ReleaseSupplyFragment fragment);

    ReleaseBuyingActivity inject(ReleaseBuyingActivity activity);

    ReleaseSupplyingActivity inject(ReleaseSupplyingActivity activity);

    BuyDetailsActivity inject(BuyDetailsActivity activity);

    SupplyDetailsActivity inject(SupplyDetailsActivity activity);

    MyBrowseActivity inject(MyBrowseActivity activity);

    BrowseBuyFragment inject(BrowseBuyFragment fragment);

    BrowseSalesFragment inject(BrowseSalesFragment fragment);

    MyCollectionActivity inject(MyCollectionActivity activity);

    CollectionBuyFragment inject(CollectionBuyFragment fragment);

    CollectionSalesFragment inject(CollectionSalesFragment fragment);

    MarketSearchActivity inject(MarketSearchActivity activity);

}
