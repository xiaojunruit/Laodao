package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.IntegralDetail;
import com.laoodao.smartagri.bean.base.Page;
import com.laoodao.smartagri.bean.base.Pagination;

import java.util.List;

/**
 * Created by Long-PC on 2017/4/13.
 * 积分详情Contact
 */

public interface IntegralDetailContract {
    interface IntegralDetailView extends ListBaseView{
        void initData(Page<IntegralDetail> integralDetailList, boolean isRefresh);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestData(int page);
    }
}
