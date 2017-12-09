package com.laoodao.smartagri.ui.market.fragment;

import android.support.v4.content.ContextCompat;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.event.DelSaleEvent;
import com.laoodao.smartagri.event.ReleaseSalesEvent;
import com.laoodao.smartagri.event.SelectAreaEvent;
import com.laoodao.smartagri.ui.market.adapter.SalesAdapter;
import com.laoodao.smartagri.ui.market.contact.SalesContact;
import com.laoodao.smartagri.ui.market.presenter.SalesPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.cityselector.activity.AreaSelectorActivity;
import com.laoodao.smartagri.view.selectionLayout.SelectionItem;
import com.laoodao.smartagri.view.selectionLayout.SelectionLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * 供销页面
 */

public class SalesFragment extends BaseXRVFragment<SalesPresenter> implements SalesContact.SalesView {


    @BindView(R.id.selectionLayout)
    SelectionLayout mSelectionLayout;
    private String areaId;
    private String categoryId;
    private String timeId;
    private List<SelectionItem> selectionItems = new ArrayList<>();

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
        List<String> menu = Arrays.asList(getResources().getStringArray(R.array.market_menu));
        mSelectionLayout.setTitle(menu);
        initAdapter(SalesAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);
    }

    private int mRequestCode = 100001;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectCity(SelectAreaEvent event) {
        SelectionLayout.ChildView view = (SelectionLayout.ChildView) mSelectionLayout.getChildAt(1);
        if (mRequestCode == event.requestCode) {
            if (event.city != null) {
                areaId = String.valueOf(event.city.id);
                view.setTitle(event.city.name);
            } else {
                areaId = "0";
                view.setTitle("全国");
            }
            onRefresh();
        }
    }


    @Override
    public void getDateMenuList(List<SupplyMenu> dateType) {
        mSelectionLayout.setMenuList(2, dateType);
    }

    @Override
    public void getCategoryMenuList(List<SupplyMenu> menuType) {
        mSelectionLayout.setMenuList(0, menuType);
    }



    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestSales(page, Constant.SUPPLY, "", categoryId, areaId, timeId);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestSales(page, Constant.SUPPLY, "", categoryId, areaId, timeId);
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void delSaleEvent(DelSaleEvent event){
        onRefresh();
    }

    @Subscribe
    public void releaseSale(ReleaseSalesEvent event) {
        if (event.type == 2) {
            if (mAdapter == null) {
                initAdapter(SalesAdapter.class);
            }
            onRefresh();
        }
    }

    @Override
    protected void lazyFetchData() {
        super.lazyFetchData();
        onRefresh();
        mSelectionLayout.onSelectedListener((clickPosition,position, data) -> {
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
            AreaSelectorActivity.start(mActivity, 2, mRequestCode);
        });
        mPresenter.requestDateType();
        mPresenter.requestMenuType();


    }
}
