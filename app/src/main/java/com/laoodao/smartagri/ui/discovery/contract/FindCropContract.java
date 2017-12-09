package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.Baike;
import com.laoodao.smartagri.bean.BaikeAllMenu;
import com.laoodao.smartagri.bean.BaikeMenu;
import com.laoodao.smartagri.bean.CateList;
import com.laoodao.smartagri.bean.Crop;

import java.util.List;


/**
 * Created by Administrator on 2017/5/19.
 */

public interface FindCropContract {


    interface FindCropView extends ListBaseView {
        void showSelectCrop(BaikeMenu menu);

        void shouAllCrop(BaikeAllMenu allMenu);
        void addCrop();

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestSelectCrop();

        void requestAllCrop();
        void requestAddCrop(String gcPaths);
    }
}