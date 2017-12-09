package com.laoodao.smartagri.ui.farmland.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ejz.imageSelector.activity.ImagePreviewActivity;
import com.ejz.imageSelector.activity.ImageSelectorActivity;
import com.ejz.imageSelector.bean.LocalMedia;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.FarmlandDetailInfo;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.event.CityEvent;
import com.laoodao.smartagri.event.FarmlandChangeEvent;
import com.laoodao.smartagri.event.SelectAreaEvent;
import com.laoodao.smartagri.ui.farmland.adapter.FarmlandImagePickerAdapter;
import com.laoodao.smartagri.ui.farmland.contract.AddFarmlandContract;
import com.laoodao.smartagri.ui.farmland.presenter.AddFarmlandPresenter;
import com.laoodao.smartagri.ui.user.adapter.ImagePickerAdapter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.cityselector.activity.CitySelectorActivity;
import com.laoodao.smartagri.view.cityselector.activity.AreaSelectorActivity;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class AddFarmlandActivity extends BaseActivity<AddFarmlandPresenter> implements AddFarmlandContract.AddFarmlandView {


    @BindView(R.id.tv_toolbar_title)
    TextView mTvToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_crop_name)
    EditText mEtCropName;
    @BindView(R.id.et_farmland_desc)
    EditText mEtFarmlandDesc;
    @BindView(R.id.iv_choose_image)
    ImageView mIvChooseImage;
    @BindView(R.id.et_area)
    EditText mEtArea;
    @BindView(R.id.btn_choose_location)
    LinearLayout mBtnChooseLocation;
    @BindView(R.id.et_address_desc)
    EditText mEtAddressDesc;
    @BindView(R.id.rtv_commit)
    RoundTextView mRtvCommit;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    private FarmlandImagePickerAdapter mAdapter;
    private int maxSelectNum = 3;
    private List<LocalMedia> mSelectedImageList = new ArrayList<>();
    private FarmlandDetailInfo detailInfo;
    private String mCityId = "";

    public static void start(Context context, FarmlandDetailInfo detailInfo) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", detailInfo);
        UiUtils.startActivity(context, AddFarmlandActivity.class, bundle);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_farmland;
    }

    @Override
    protected void configViews() {
        detailInfo = (FarmlandDetailInfo) getIntent().getSerializableExtra("detail");
        mAdapter = new FarmlandImagePickerAdapter(this, maxSelectNum);
        mRecyclerview.setAdapter(mAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        mAdapter.setOnItemClickListener(position -> {
            ImagePreviewActivity.startPreview(this, mSelectedImageList, mSelectedImageList, mSelectedImageList.size(), position, true);
        });
        if (detailInfo != null) {
            setTitle("编辑农田");
            mEtCropName.setText(detailInfo.cropsName);
            mEtFarmlandDesc.setText(detailInfo.farmlandDesc);
            mEtArea.setText(detailInfo.acreage);
            mEtAddressDesc.setText(detailInfo.address);

            mTvAddress.setText(detailInfo.areaName);

            if (detailInfo.imgArr != null) {
                List<LocalMedia> selectedImageList = new ArrayList<>();
                for (String s : detailInfo.imgArr) {
                    LocalMedia local = new LocalMedia(s);
                    selectedImageList.add(local);
                }
                addImage(selectedImageList);
            }
        }
    }


    @Override
    public void complete() {

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
            mCityId = String.valueOf(event.province.id);
        }
        if (event.city != null) {
            address += "/" + event.city.name;
            mCityId = String.valueOf(event.city.id);
        }

        if (event.county != null) {
            address += "/" + event.county.name;
            mCityId = String.valueOf(event.county.id);
        }
        if (event.town != null) {
            address += "/" + event.town.name;
            mCityId = String.valueOf(event.town.id);
        }


        mTvAddress.setText(address);
    }

    @OnClick({R.id.iv_choose_image, R.id.btn_choose_location, R.id.rtv_commit, R.id.tv_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_image:
                ImageSelectorActivity.start(this, maxSelectNum - mAdapter.getItemCount(), true);
                break;
            case R.id.btn_choose_location:
                break;
            case R.id.rtv_commit:
                addFarmland();
                break;
            case R.id.tv_address:
                UiUtils.startActivity(AreaSelectorActivity.class);
                break;
        }
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
        mAdapter.clear();
        mAdapter.addAll(mSelectedImageList);
        boolean isVisible = mSelectedImageList.size() < maxSelectNum;
        if (isVisible) {
            visible(mIvChooseImage);
        } else {
            gone(mIvChooseImage);
        }

    }

    //判断是不是后台返回的网络图片
    private void isNetworkImg() {
        if (mSelectedImageList.size() != 0) {
            for (int i = mSelectedImageList.size() - 1; i >= 0; i--) {
                if (mSelectedImageList.get(i).getPath().startsWith("http://") || mSelectedImageList.get(i).getPath().startsWith("https://")) {
                    mSelectedImageList.remove(i);
                }
            }
        }
    }

    private void addFarmland() {
        isNetworkImg();
        String cropName = mEtCropName.getText().toString();
        String farmlandDesc = mEtFarmlandDesc.getText().toString();
        String acreage = mEtArea.getText().toString();
        String addressDesc = mEtAddressDesc.getText().toString();
        if (detailInfo != null) {
            mCityId = detailInfo.areaId;
        }
        if (TextUtils.isEmpty(mCityId)) {
            mCityId = "";
        }
        mPresenter.addFarmland(this, detailInfo == null ? 0 : detailInfo.id, cropName, farmlandDesc, acreage, addressDesc, mCityId, mSelectedImageList);
    }

    @Override
    public void addSuccess() {
        EventBus.getDefault().post(new FarmlandChangeEvent());
        finish();
    }
}