package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.CropCategory;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.PriceQuotation;
import com.laoodao.smartagri.bean.SupplyMenu;

import java.util.List;

/**
 * Created by WORK on 2017/7/28.
 */

public interface PriceQuotationContract {
    interface PriceQuotationView extends ListBaseView {
        void setDateMenuList(List<SupplyMenu> dateList);

        void setCropMenuList(List<SupplyMenu> dateList);

        void setPriceQuotation(List<PriceQuotation> priceQuotation);

        void setMessage(boolean message);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestData(int cateId, int page, int timeType, String keyword, String pos, int areaId);

        void requestDateType();

        void requestCropType();

        void addCottonFieldWonder(int id);

        void isMessage();

    }
}
