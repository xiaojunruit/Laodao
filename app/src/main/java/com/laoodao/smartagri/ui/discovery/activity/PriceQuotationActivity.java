package com.laoodao.smartagri.ui.discovery.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ejz.multistateview.MultiStateView;
import com.flyco.tablayout.widget.MsgView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.Keyword;
import com.laoodao.smartagri.bean.PriceQuotation;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.PriceQuotationFollowEvent;
import com.laoodao.smartagri.event.SelectAreaEvent;
import com.laoodao.smartagri.ui.discovery.adapter.PriceQuotationAdapter;
import com.laoodao.smartagri.ui.discovery.contract.PriceQuotationContract;
import com.laoodao.smartagri.ui.discovery.presenter.PriceQuotationPresenter;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.ChildView;
import com.laoodao.smartagri.view.cityselector.activity.AreaSelectorActivity;
import com.laoodao.smartagri.view.selectionLayout.SelectionTwoDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/7/28.
 */

public class PriceQuotationActivity extends BaseXRVActivity<PriceQuotationPresenter> implements PriceQuotationContract.PriceQuotationView {
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.msg_dot)
    MsgView mMsgDot;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private int cropId;//分类id
    private int timeId;//时间id
    public final int requestCode = 100003;
    private String areaName;//城市名称
    private int areaId;//城市id
    private String[] title;
    private List<SupplyMenu> cropData;
    private List<SupplyMenu> timeData;
    private SelectLocation currentLocation;
    private String pos = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_price_quotation;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    private void initChildView() {
        for (int i = 0; i < title.length; i++) {
            ChildView childView = new ChildView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            childView.setLayoutParams(params);
            childView.setTag(i);
            childView.setTitle(title[i]);
            mLlContent.addView(childView);
            SelectionTwoDialog mSelectionDialog = new SelectionTwoDialog(this, mLlContent);
            childView.setOnItemClickListener((view, position) -> {
                switch (position) {
                    case 0:
                        if (cropData.size() < 1) {
                            break;
                        }
                        mSelectionDialog.setIsGrid(true);
                        mSelectionDialog.setData(cropData);
                        childView.selected();
                        mSelectionDialog.show();
                        break;
                    case 1:
                        AreaSelectorActivity.start(this, 2, requestCode, areaName);
                        break;
                    case 2:
                        if (timeData.size() < 1) {
                            break;
                        }
                        mSelectionDialog.setIsGrid(false);
                        mSelectionDialog.setData(timeData);
                        childView.selected();
                        mSelectionDialog.show();
                        break;
                }
            });
            mSelectionDialog.setOnDismissListener(dialog -> {
                childView.selected();
            });
            int num = i;
            mSelectionDialog.setOnItemClick((position, id, name) -> {
                switch (num) {
                    case 0:
                        cropId = Integer.parseInt(id);
                        break;
                    case 2:
                        timeId = Integer.parseInt(id);
                        break;
                }
                childView.setTitle(name);
                onRefresh();
                mSelectionDialog.dismiss();

            });
        }
    }


    @Override
    protected void configViews() {
        currentLocation = Global.getInstance().getCurrentLocation();

        if (currentLocation != null) {
            pos = currentLocation.longitude + "," + currentLocation.latitude;

        }
        cropData = new ArrayList<>();
        timeData = new ArrayList<>();
        title = UiUtils.getStringArray(R.array.price_quotation_menu);
        initChildView();
        initAdapter(PriceQuotationAdapter.class);
        List<String> titles = Arrays.asList(UiUtils.getStringArray(R.array.price_quotation_menu));
        mPresenter.requestCropType();
        mPresenter.requestDateType();
        onRefresh();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        ((PriceQuotationAdapter) mAdapter).onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectCity(SelectAreaEvent event) {
        if (event.requestCode == requestCode) {
            ChildView view = (ChildView) mLlContent.getChildAt(1);
            if (event.city != null) {
                areaId = (int) event.city.id;
                view.setTitle(event.city.name);
            } else {
                areaId = 0;
                view.setTitle("全国");
            }
            areaName = view.getTitle();
            onRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void PriceQuotationFollowChange(PriceQuotationFollowEvent event) {
        mPresenter.addCottonFieldWonder(event.id);
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.isMessage();
        mPresenter.requestData(cropId, page, timeId, "", pos, areaId);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.requestData(cropId, page, timeId, "", pos, areaId);
    }

    /**
     * 日期菜单
     *
     * @param dateList
     */
    @Override
    public void setDateMenuList(List<SupplyMenu> dateList) {
        timeData.clear();
        timeData.addAll(dateList);
    }

    @Override
    public void setCropMenuList(List<SupplyMenu> dateList) {
        cropData.clear();
        cropData.addAll(dateList);
    }

    @Override
    public void setPriceQuotation(List<PriceQuotation> priceQuotation) {
        mAdapter.addAll(priceQuotation, true);
    }


    @Override
    public void setMessage(boolean message) {
        if (message)
            mMsgDot.setVisibility(View.VISIBLE);
        else
            mMsgDot.setVisibility(View.INVISIBLE);
    }


    @OnClick({R.id.iv_back, R.id.message, R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                UiUtils.startActivity(PriceQuotationSearchActivity.class);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.message:
                if (Global.getInstance().isLoggedIn()) {
                    UiUtils.startActivity(MyAttentionPriceActivity.class);
                } else {
                    UiUtils.startActivity(LoginActivity.class);
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Global.getInstance().isLoggedIn()) {
            mPresenter.isMessage();
        } else {
            mMsgDot.setVisibility(View.INVISIBLE);
        }
    }
}
