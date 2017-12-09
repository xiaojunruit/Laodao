package com.laoodao.smartagri.ui.market.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.ejz.imageSelector.activity.ImagePreviewActivity;
import com.ejz.imageSelector.activity.ImageSelectorActivity;
import com.ejz.imageSelector.bean.LocalMedia;
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
import com.laoodao.smartagri.event.ReleaseSalesEvent;
import com.laoodao.smartagri.event.SelectAreaEvent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.ui.market.adapter.ImagePickerAdapter;
import com.laoodao.smartagri.ui.market.contact.ReleaseSupplyingContact;
import com.laoodao.smartagri.ui.market.dialog.SelectSecondTypeDialog;
import com.laoodao.smartagri.ui.market.dialog.SelectTypeDialog;
import com.laoodao.smartagri.ui.market.presenter.ReleaseSupplyingPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.laoodao.smartagri.view.cityselector.activity.AreaSelectorActivity;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ReleaseSupplyingActivity extends BaseActivity<ReleaseSupplyingPresenter> implements ReleaseSupplyingContact.ReleaseSupplyingView {

    @BindView(R.id.iv_choose_image)
    ImageView mIvChooseImage;
    @BindView(R.id.btn_commit)
    RoundTextView mBtnCommit;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Order(1)
    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @BindView(R.id.et_price)
    EditText mEtPrice;
    @BindView(R.id.et_num)
    EditText mEtNum;
    @BindView(R.id.et_acreage)
    EditText mEtAcreage;
    @Order(3)
    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.et_username)
    EditText mEtUsername;
    @Order(4)
    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_mark)
    EditText mEtMark;
    @BindView(R.id.type)
    LinearLayout mType;
    @Order(6)
    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.tv_type)
    TextView mTvType;
    @Order(7)
    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.tv_type_second)
    TextView mTvTypeSecond;
    @BindView(R.id.ll_acreage)
    LinearLayout mLlAcreage;
    @BindView(R.id.type_second)
    LinearLayout mTypeSecond;
    @BindView(R.id.ll_price)
    LinearLayout mLlPrice;
    @BindView(R.id.ll_amount)
    LinearLayout mLlAmount;
    @BindView(R.id.tv_area)
    TextView mTvArea;

    @BindView(R.id.tv_types)
    TextView mTvTypes;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_type_seconds)
    TextView mTvTypeSeconds;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_acreage)
    TextView mTvAcreage;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private String title;


    private int id;
    private ImagePickerAdapter mImageAdapter;
    private List<LocalMedia> mSelectedImageList = new ArrayList<>();
    private int maxSelectNum = 7;
    private DefaultValidator mValidator;
    private SelectTypeDialog dialog;
    private SelectSecondTypeDialog secondDialog;
    private List<SupplyMenu> menuList = new ArrayList<>();

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
        UiUtils.startActivity(context, ReleaseSupplyingActivity.class, bundle);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_supplying;
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
        mPresenter.requestType(1);
        mTvTypes.setText(Html.fromHtml(UiUtils.getString(R.string.market_supply_type)));
        mTvTitle.setText(Html.fromHtml(UiUtils.getString(R.string.market_title)));
        mTvTypeSeconds.setText(Html.fromHtml(UiUtils.getString(R.string.market_type_second)));
        mTvName.setText(Html.fromHtml(UiUtils.getString(R.string.market_name)));
        mTvPhone.setText(Html.fromHtml(UiUtils.getString(R.string.market_phone)));
        mTvAddress.setText(Html.fromHtml(UiUtils.getString(R.string.market_address)));
        mTvAcreage.setText(Html.fromHtml(UiUtils.getString(R.string.market_acreage)));
        mImageAdapter = new ImagePickerAdapter(this);
        mValidator = new DefaultValidator(this);
        title = getIntent().getStringExtra("title");
        id = getIntent().getIntExtra("id", 0);
        setTitle(title);
        if (id == -1) {
            initLocation();
            User user = Global.getInstance().getUserInfo();
            mEtPhone.setText(user.mobile);
        } else {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
            //mPresenter.requestDetail(id);
        }
        mRecyclerview.setAdapter(mImageAdapter);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mImageAdapter.setOnItemClickListener(position -> {
            ImagePreviewActivity.startPreview(this, mSelectedImageList, mSelectedImageList, mSelectedImageList.size(), position, true);
        });

        mImageAdapter.setOnAddImageClickListener(() -> {
            ImageSelectorActivity.start(this, maxSelectNum - mImageAdapter.getItemCount(), true);
        });
        mValidator.clicked(mBtnCommit).succeeded(() -> {
            isNetworkImg();
            String goodsName = mEtTitle.getText().toString();
            String price = mEtPrice.getText().toString();
            String num = mEtNum.getText().toString();
            String acreage = mEtAcreage.getText().toString();
            String username = mEtUsername.getText().toString();
            String phone = mEtPhone.getText().toString();
            String mark = mEtMark.getText().toString();
            String category = mTvTypeSecond.getTag().toString();
            String topcategory = mTvType.getTag().toString();
            String area = mTvArea.getText().toString();
            if ("土地".equals(mTvType.getText().toString())) {
                if (TextUtils.isEmpty(acreage)) {
                    UiUtils.makeText(UiUtils.getString(R.string.required));
                    return;
                }
            }
            if (TextUtils.isEmpty(cityId) && TextUtils.isEmpty(mLatitude)) {
                UiUtils.makeText("定位失败请手动选择位置");
                return;
            }
            if (id == -1) {
                mPresenter.request(this, 1, category, goodsName, price, num, username, phone, area, mark, mSelectedImageList, acreage, topcategory, mLatitude, mLongitude, cityId);

            } else {
                mPresenter.requesEdit(this, id, 1, category, goodsName, price, num, username, phone, area, mark, mSelectedImageList, acreage, topcategory, mLatitude, mLongitude, cityId);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
                List<LocalMedia> imageList = (ArrayList) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                addImage(imageList);
            } else if (requestCode == ImagePreviewActivity.REQUEST_PREVIEW) {
                List<LocalMedia> imageList = (ArrayList) data.getSerializableExtra(ImagePreviewActivity.OUTPUT_LIST);
                mSelectedImageList.clear();
                addImage(imageList);
            }
        }
    }

    private void addImage(List<LocalMedia> imageList) {
        mSelectedImageList.addAll(imageList);
        mImageAdapter.clear();
        mImageAdapter.addAll(mSelectedImageList);
    }

    private void isNetworkImg() {
        if (mSelectedImageList.size() != 0) {
            for (int i = mSelectedImageList.size() - 1; i >= 0; i--) {
                if (mSelectedImageList.get(i).getPath().startsWith("http://") || mSelectedImageList.get(i).getPath().startsWith("https://")) {
                    mSelectedImageList.remove(i);
                }
            }
        }
    }

    @OnClick({R.id.iv_choose_image, R.id.type, R.id.type_second, R.id.tv_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_image:
                ImageSelectorActivity.start(this, maxSelectNum - mImageAdapter.getItemCount(), true);
                break;
            case R.id.type:
                typeDialog();
                break;
            case R.id.type_second:
                typeSecondDialog();
                break;
            case R.id.tv_area:
                UiUtils.startActivity(AreaSelectorActivity.class);
                break;
        }
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
            mTvType.setTag(selectionItem.id);
            if (menuList.size() != 0)
                secondDialog = new SelectSecondTypeDialog(this, menuList.get(item).items);

            mLlAmount.setVisibility(item == 0 ? View.VISIBLE : View.GONE);
            mLlAcreage.setVisibility(item == 1 ? View.VISIBLE : View.GONE);
            if (secondItem != item) {
                mTvTypeSecond.setText("");
                mEtAcreage.setText("");
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
            mTvTypeSecond.setTag(selectionItem.id);
        });
    }


    @Override
    public void addSupply() {
        EventBus.getDefault().post(new ReleaseSalesEvent(2));
        finish();
    }


    @Override
    public void typeSuccess(List<SupplyMenu> menuSupplies) {
        menuSupplies.remove(0);
        dialog = new SelectTypeDialog(this, mType, menuSupplies);
        menuList = menuSupplies;
        if (id != -1) {
            mPresenter.requestDetail(id);
        }
    }

    @Override
    public void getDetail(SupplyDetail detail) {
        mTvType.setText(detail.category);
        mTvType.setTag(String.valueOf(detail.topcategory));
        mEtTitle.setText(detail.title);
        mEtMark.setText(detail.content);
        mEtNum.setText(detail.amount);
        mEtUsername.setText(detail.contactor);
        mEtPhone.setText(detail.contacmobile);
        mEtAcreage.setText(detail.acreage);
        mEtPrice.setText(String.valueOf(detail.price));
        mTvTypeSecond.setText(detail.categoryName);
        mTvTypeSecond.setTag(detail.categoryId);
        mTvArea.setText(detail.areaInfo);
        cityId = detail.areaId;
        mLlAmount.setVisibility("农副产品".equals(detail.category) ? View.VISIBLE : View.GONE);
        mLlAcreage.setVisibility("土地".equals(detail.category) ? View.VISIBLE : View.GONE);
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
        List<LocalMedia> selectImg = new ArrayList<>();
        for (String s : detail.thumb) {
            LocalMedia localMedia = new LocalMedia(s);
            selectImg.add(localMedia);
        }

        addImage(selectImg);

    }

    @Override
    public void editSupply() {
        EventBus.getDefault().post(new ReleaseSalesEvent(1));
        finish();
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
