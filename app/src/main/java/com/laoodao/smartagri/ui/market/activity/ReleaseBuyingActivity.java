package com.laoodao.smartagri.ui.market.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.ejz.multistateview.MultiStateView;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.SupplyDetail;
import com.laoodao.smartagri.bean.SupplyMenu;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerMarketComponent;
import com.laoodao.smartagri.event.ReleaseBuyEvent;
import com.laoodao.smartagri.event.SelectAreaEvent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.ui.market.contact.ReleaseBuyingContact;
import com.laoodao.smartagri.ui.market.dialog.SelectSecondTypeDialog;
import com.laoodao.smartagri.ui.market.dialog.SelectTypeDialog;
import com.laoodao.smartagri.ui.market.presenter.ReleaseBuyingPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.cityselector.activity.AreaSelectorActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ReleaseBuyingActivity extends BaseActivity<ReleaseBuyingPresenter> implements ReleaseBuyingContact.ReleaseBuyingView {

    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_amount)
    EditText mEtAmount;

    @BindView(R.id.et_contactor)
    EditText mEtContactor;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.tv_area)
    TextView mTvArea;
    @BindView(R.id.btn_commit)
    RoundTextView mBtnCommit;
    @BindView(R.id.et_contacmobile)
    EditText mEtContacmobile;
    @BindView(R.id.tv_type_second)
    TextView mTvTypeSecond;
    @BindView(R.id.type_second)
    LinearLayout mTypeSecond;
    @BindView(R.id.ll_amount)
    LinearLayout mLlAmount;
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.ll_price)
    LinearLayout mLlPrice;
    @BindView(R.id.et_acreage)
    EditText mEtAcreage;
    @BindView(R.id.ll_acreage)
    LinearLayout mLlAcreage;
    @BindView(R.id.tv_types)

    TextView mTvTypes;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_type_seconds)
    TextView mTvTypeSeconds;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.ll_contactor)
    LinearLayout mLlContactor;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private SelectTypeDialog dialog;
    private SelectSecondTypeDialog secondDialog;
    private List<SupplyMenu> menuList = new ArrayList<>();

    private String title;
    private int id;

    private String cityId = "";
    private String mLatitude = "";
    private String mLongitude = "";
    private int item = 0;
    private int secondItem = 0;
    private String mArea;

    @Override
    public void complete() {

    }

    public static void start(Context context, String title, int id) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putInt("id", id);
        UiUtils.startActivity(context, ReleaseBuyingActivity.class, bundle);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_buying;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerMarketComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        mTvTypes.setText(Html.fromHtml(UiUtils.getString(R.string.market_type)));
        mTvTitle.setText(Html.fromHtml(UiUtils.getString(R.string.market_title)));
        mTvTypeSeconds.setText(Html.fromHtml(UiUtils.getString(R.string.market_type_second)));
        mTvName.setText(Html.fromHtml(UiUtils.getString(R.string.market_name)));
        mTvPhone.setText(Html.fromHtml(UiUtils.getString(R.string.market_phone)));
        mTvAddress.setText(Html.fromHtml(UiUtils.getString(R.string.market_address)));

        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id", 0);
        setTitle(title);
        mPresenter.requestMenu(1);
        if (id == -1) {
            initLocation();
            User user = Global.getInstance().getUserInfo();
            mEtContacmobile.setText(user.mobile);
        } else {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            // mPresenter.requestDetail(id);
        }

    }


    @Override
    public void addBuy() {
        EventBus.getDefault().post(new ReleaseBuyEvent(2));
        finish();
    }

    @Override
    public void getBuyDetail(SupplyDetail detail) {
        mTvType.setText(detail.category);
        mTvType.setTag(String.valueOf(detail.topcategory));
        mEtTitle.setText(detail.title);
        mEtContent.setText(detail.content);
        mEtAmount.setText(detail.amount);
        mEtContactor.setText(detail.contactor);
        mEtContacmobile.setText(detail.contacmobile);
        mEtAcreage.setText(detail.acreage);
        mEtPrice.setText(String.valueOf(detail.price));
        mTvTypeSecond.setText(detail.categoryName);
        mTvTypeSecond.setTag(detail.categoryId);
        mTvArea.setText(detail.areaInfo);
        cityId = detail.areaId;
        item = 0;
        if ("农副产品".equals(detail.category)) {
            item = 0;
        } else if ("土地".equals(detail.category)) {
            item = 1;
        } else {
            item = 2;
        }
        if (menuList.size() != 0) {
            secondDialog = new SelectSecondTypeDialog(this, menuList.get(item).items);
        }
        mLlAmount.setVisibility("农副产品".equals(detail.category) ? View.VISIBLE : View.GONE);
        mLlAcreage.setVisibility("土地".equals(detail.category) ? View.VISIBLE : View.GONE);

    }


    @Override
    public void getMenu(List<SupplyMenu> item) {
        item.remove(0);
        dialog = new SelectTypeDialog(this, mTvType, item);
        menuList = item;
        if (id != -1) {
            mPresenter.requestDetail(id);
        }

    }

    @Override
    public void editBuy() {
        EventBus.getDefault().post(new ReleaseBuyEvent(1));
        finish();
    }


    private void typeDialog() {
        if (dialog != null) {
            dialog.show();
        } else {
            return;
        }
        dialog.setOnSelectedResultListener(selectionItem -> {
            item = 0;
            if (selectionItem.name.equals("农副产品")) {
                item = 0;
            } else if (selectionItem.name.equals("土地")) {
                item = 1;
            } else {
                item = 2;
            }
            mTvType.setText(selectionItem.name);
            mTvType.setTag(String.valueOf(selectionItem.id));
            if (menuList.size() != 0) {
                secondDialog = new SelectSecondTypeDialog(this, menuList.get(item).items);
            }
            mLlAmount.setVisibility(item == 0 ? View.VISIBLE : View.GONE);
            mLlAcreage.setVisibility(item == 1 ? View.VISIBLE : View.GONE);
            if (secondItem != item) {
                mTvTypeSecond.setText("");
                mEtAcreage.setText("");
                // mTvTypeSecond.setHint("请输入您需要求购的商品类型");
            }
        });
    }


    private void typeSecondDialog() {
        if (secondDialog != null) {
            secondDialog.show();
            secondItem = item;
        } else {
            typeDialog();
            return;
        }
        secondDialog.setOnSelectedResultListener(selectionItem -> {
            mTvTypeSecond.setText(selectionItem.name);
            mTvTypeSecond.setTag(String.valueOf(selectionItem.id));
        });
    }

    @OnClick({R.id.tv_type, R.id.tv_area, R.id.btn_commit, R.id.tv_type_second})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_type:
                typeDialog();
                break;
            case R.id.tv_type_second:
                typeSecondDialog();
                break;
            case R.id.tv_area:
                UiUtils.startActivity(AreaSelectorActivity.class);
                break;
            case R.id.btn_commit:
                submitForm();
                break;
        }
    }

    public void submitForm() {
        String title = mEtTitle.getText().toString();
        String amount = mEtAmount.getText().toString();
        String phone = mEtContacmobile.getText().toString();
        String contactor = mEtContactor.getText().toString();
        String content = mEtContent.getText().toString();

        String area = mArea;
        String price = mEtPrice.getText().toString();
        String acreage = mEtAcreage.getText().toString();
        String topcategory = "";
        String category = "";
        if (mTvTypeSecond.getTag() != null && mTvType.getTag() != null) {
            category = mTvTypeSecond.getTag().toString();
            topcategory = mTvType.getTag().toString();
        }
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(category) || TextUtils.isEmpty(contactor) || TextUtils.isEmpty(area) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(topcategory)) {
            UiUtils.makeText("带 * 号为必填项");
            return;
        }
        if (TextUtils.isEmpty(cityId) && TextUtils.isEmpty(mLatitude)) {
            UiUtils.makeText("定位失败请手动选择位置");
            return;
        }
        if (id == -1) {
            mPresenter.request(this, "2", category, title, amount, phone, contactor, area, content, price, acreage, topcategory, cityId, mLatitude, mLongitude);
        } else {
            mPresenter.requesEdit(this, id, "2", category, title, amount, phone, contactor, area, content, price, acreage, topcategory, cityId, mLatitude, mLongitude);
        }
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe
    public void selectCity(SelectAreaEvent event) {
        String address = "";
        if (event.province != null) {
            address = event.province.name;
            mArea = event.province.name;
            cityId = String.valueOf(event.province.id);
        }
        if (event.city != null) {
            address += event.city.name;
            mArea += "/" + event.city.name;
            cityId = String.valueOf(event.city.id);
        }

        if (event.county != null) {
            address += event.county.name;
            mArea += "/" + event.county.name;
            cityId = String.valueOf(event.county.id);
        }
        if (event.town != null) {
            address += event.town.name;
            mArea += "/" + event.town.name;
            cityId = String.valueOf(event.town.id);
        }
        mTvArea.setText(address);
    }

    private void initLocation() {
        RxLocation.get().locate(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(new LocationSubscriber() {
                    @Override
                    public void onLocatedSuccess(@NonNull BDLocation bdLocation) {
                        mTvArea.setText(bdLocation.getCity());
                        mArea = bdLocation.getCity();
                        mLatitude = String.valueOf(bdLocation.getLatitude());
                        mLongitude = String.valueOf(bdLocation.getLongitude());
                    }

                    @Override
                    public void onLocatedFail(BDLocation bdLocation) {
                        mTvArea.setHint("定位失败");
                    }
                });
    }

    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onEmpty() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onContent() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public <Item> void setResult(List<Item> items, boolean isRefresh) {

    }
}
