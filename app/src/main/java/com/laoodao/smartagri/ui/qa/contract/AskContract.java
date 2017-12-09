package com.laoodao.smartagri.ui.qa.contract;

import android.app.Activity;
import android.content.Context;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.AskSuccess;
import com.laoodao.smartagri.bean.Plant;

import java.util.List;

/**
 * Created by WORK on 2017/4/18.
 */

public interface AskContract {
    interface AskView extends BaseView {
        void releaseSuccess(AskSuccess success);

    }

    interface Presenter<T> extends BasePresenter<T> {
        void releaseQuetion(Activity activity, String title, String content, String longitude, String latitude, String city, List<Plant> plantList, List<LocalMedia> selectedImageList);

    }
}
