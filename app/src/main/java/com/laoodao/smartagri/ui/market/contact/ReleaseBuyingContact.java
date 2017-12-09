package com.laoodao.smartagri.ui.market.contact;

import android.app.Activity;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.SupplyDetail;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface ReleaseBuyingContact {
    interface ReleaseBuyingView extends ListBaseView {
        void addBuy();

        void getBuyDetail(SupplyDetail detail);

        void getMenu(List<SupplyMenu> item);

        void editBuy();

    }

    interface Presenter<T> extends BasePresenter<T> {
        void request(Activity activity,String type, String category, String title, String amount, String contacmobile, String contactor, String area, String content
                , String price, String acreage, String topcategory, String areaId, String mLatitude, String mLongitude);

        void requestDetail(int id);

        void requestMenu(int id);

        void requesEdit(Activity activity,int id, String type, String category, String title, String amount, String contacmobile, String contactor, String area, String content
                , String price, String acreage, String topcategory, String areaId, String mLatitude, String mLongitude);
    }
}
