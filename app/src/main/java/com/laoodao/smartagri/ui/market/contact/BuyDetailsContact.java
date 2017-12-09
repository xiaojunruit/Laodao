package com.laoodao.smartagri.ui.market.contact;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.ChatGoodsInit;
import com.laoodao.smartagri.bean.SupplyDetail;

/**
 * Created by Administrator on 2017/4/19.
 */

public interface BuyDetailsContact {
    interface BuyDetailsView extends ListBaseView {
        void initSupplyDetail(SupplyDetail detail);

        void addCollection();

        void delCollection();

        void setChatGoodsInit(ChatGoodsInit data);
    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestSupplyDetail(int id);

        void requestAdd(int itemId);

        void requestDel(int itemId);

        void shareBack(String tag,int id);

        void addTellog(int id);

        void chatInit(String id,String tag);
    }
}
