package com.laoodao.smartagri.ui.market.contact;

import android.app.Activity;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.SupplyDetail;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface ReleaseSupplyingContact {
    interface ReleaseSupplyingView extends ListBaseView {
        void addSupply();

        void typeSuccess(List<SupplyMenu> menuSupplies);

        void getDetail(SupplyDetail detail);

        void editSupply();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void request(Activity activity, int type, String category, String title, String price, String amount, String contactor, String contacmobile, String area
                , String content, List<LocalMedia> conver, String acreage, String topcategory, String mLatitude, String mLongitude, String areaId);

        void requestType(int id);

        void requestDetail(int id);

        void requesEdit(Activity activity,int id, int type, String category, String title, String price, String amount, String contactor, String contacmobile, String area
                , String content, List<LocalMedia> conver, String acreage, String topcategory, String mLatitude, String mLongitude,String areaId);
    }
}
