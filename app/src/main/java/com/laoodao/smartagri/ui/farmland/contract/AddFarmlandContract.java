package com.laoodao.smartagri.ui.farmland.contract;

import android.app.Activity;
import android.content.Context;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;

import java.util.List;


/**
 * Created by 欧源 on 2017/4/24.
 */

public interface AddFarmlandContract {


    interface AddFarmlandView extends BaseView {

        void addSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void addFarmland(Activity context, int id, String cropName, String farmlandDesc, String acreage, String addressDesc,
                         String provinceId, List<LocalMedia> mSelectedImageList);
    }
}