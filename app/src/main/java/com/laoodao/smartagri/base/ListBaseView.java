package com.laoodao.smartagri.base;

import com.laoodao.smartagri.bean.base.Page;

import java.util.List;

/**
 * Created by Administrator on 2017/4/8.
 */

public interface ListBaseView extends BaseView {
    void noMore(boolean noMore);

    void onError();

    void onEmpty();

    void onContent();



    <Item> void setResult(List<Item> items, boolean isRefresh);
}
