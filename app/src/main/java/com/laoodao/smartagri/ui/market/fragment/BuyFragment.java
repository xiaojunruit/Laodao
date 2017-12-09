package com.laoodao.smartagri.ui.market.fragment;

import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.SupplyArea;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.event.DelBuyEvent;
import com.laoodao.smartagri.event.ReleaseBuyEvent;
import com.laoodao.smartagri.event.SelectAreaEvent;
import com.laoodao.smartagri.ui.market.adapter.BuyAdapter;
import com.laoodao.smartagri.ui.market.contact.BuyContact;
import com.laoodao.smartagri.ui.market.presenter.BuyPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.cityselector.activity.AreaSelectorActivity;
import com.laoodao.smartagri.view.selectionLayout.SelectionItem;
import com.laoodao.smartagri.view.selectionLayout.SelectionLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 求购
 */

public class BuyFragment extends BaseXRVFragment<BuyPresenter> implements BuyContact.BuyView {


    @BindView(R.id.selectionLayout)
    SelectionLayout mSelectionLayout;
    private String areaId;
    private String categoryId;
    private String timeId;
    private String areaName;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_buy_sales;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        initAdapter(BuyAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);
        List<String> menu = Arrays.asList(getResources().getStringArray(R.array.market_menu));
        mSelectionLayout.setTitle(menu);
    }

    public final int requestCode = 100002;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectCity(SelectAreaEvent event) {
        if (event.requestCode == requestCode) {
            SelectionLayout.ChildView view = (SelectionLayout.ChildView) mSelectionLayout.getChildAt(1);
            if (event.city != null) {
                areaId = String.valueOf(event.city.id);
                view.setTitle(event.city.name);
            } else {
                areaId = "0";
                view.setTitle("全国");
            }
            areaName = view.getTitle();
            onRefresh();
        }
    }

    @Override
    public void addressType(List<SupplyArea> addressType) {
    }

    /**
     * 日期菜单
     *
     * @param dateList
     */
    @Override
    public void getDateMenuList(List<SupplyMenu> dateList) {
        mSelectionLayout.setMenuList(2, dateList);
    }

    /**
     * 分类菜单
     *
     * @param categoryList
     */
    @Override
    public void getCategoryMenuList(List<SupplyMenu> categoryList) {
        mSelectionLayout.setMenuList(0, categoryList);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestBuy(page, Constant.WANT_BUY, "", categoryId, areaId, timeId);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestBuy(page, Constant.WANT_BUY, "", categoryId, areaId, timeId);
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void delBuyEvent(DelBuyEvent event) {
        onRefresh();
    }

    @Subscribe
    public void releaseBuy(ReleaseBuyEvent event) {
        if (event.type == 2) {
            if (mAdapter == null) {
                initAdapter(BuyAdapter.class);
            }
            onRefresh();
        }
    }

    @Override
    protected void lazyFetchData() {
        mSelectionLayout.onSelectedListener((clickPosition, position, data) -> {
            List<SupplyMenu> item = (List<SupplyMenu>) data;
            switch (position) {
                case 0:
                    categoryId = item.get(clickPosition).id;
                    onRefresh();
                    break;
                case 2:
                    timeId = item.get(clickPosition).id;
                    onRefresh();
                    break;
            }
        });
        mSelectionLayout.setOnItemClickListener((view, position) -> {
            AreaSelectorActivity.start(mActivity, 2, requestCode, areaName);
        });
        onRefresh();
        mPresenter.requestMenuType();
        mPresenter.requestDateType();
    }

}
