package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.NearbyShop;

import java.util.List;

/**
 * Created by WORK on 2017/5/3.
 */

public interface NearbyShopContract {
    interface NearbyShopView extends ListBaseView{
//        void nearbyShopSuccess(List<NearbyShop> nearbyShops,boolean isRefresh);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestNearbyShop(int page,String lat,String lng);
    }
}
