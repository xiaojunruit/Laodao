package com.laoodao.smartagri.ui.qa.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.Crop;
import com.laoodao.smartagri.bean.Plant;

import java.util.List;


/**
 * Created by 欧源 on 2017/4/29.
 */

public interface CropContract {


    interface CropView extends BaseView {

        void showCropList(List<Crop> data);

        void searchCropList(List<Plant> data);
    }

    interface Presenter<T> extends BasePresenter<T> {

        void getCropList();

        void getSearchCropList(int page,String keyWord);

    }
}