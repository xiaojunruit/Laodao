package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.NearbyShopDetail;

/**
 * Created by WORK on 2017/5/3.
 */

public interface NearbyShopDetailContract {
    interface NearbyShopDetailView extends ListBaseView {
        void nearbyShopDetailSuccess(NearbyShopDetail detial);

        void successFollow();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestNearbyShopDetail(String id, String lat, String lng);

        void requestFollow(String id);
    }
}
