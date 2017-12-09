package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.NewDrug;
import com.laoodao.smartagri.bean.NewDrugTab;
import com.laoodao.smartagri.bean.Unipue;

import java.util.List;

/**
 * Created by WORK on 2017/5/17.
 */

public interface CropDetailContract {
    interface CropDetailView extends ListBaseView {
        void newDrugTab(NewDrugTab newDrugTab);

        void unipueSuccess(List<Unipue> unipues);

        void newDrugListSuccess(List<NewDrug> newDrugs);

        void drugCollectSuccess();
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestNewDrugList(int id);

        void requestUnipueList(int id);

        void requestDrugCollect(int id);
    }
}
