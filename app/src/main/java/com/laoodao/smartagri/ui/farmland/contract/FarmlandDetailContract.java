package com.laoodao.smartagri.ui.farmland.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.FarmlandDetail;
import com.laoodao.smartagri.bean.FarmlandDetailInfo;

import java.util.List;

/**
 * Created by WORK on 2017/4/24.
 */

public interface FarmlandDetailContract {
    interface FarmlandDetailView extends ListBaseView {
        void requestFarmlandDetailListSuccess(List<FarmlandDetail> farmlandDetailList, boolean isRefresh);

        void requestFarmlandDetailSuccess(FarmlandDetailInfo info);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestFarmlandDetail(int id);

        void requestFarmlandDetailList(int page, int id);
    }
}
