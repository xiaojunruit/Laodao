package com.laoodao.smartagri.ui.qa.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.ejz.imageSelector.activity.ImagePreviewActivity;
import com.ejz.imageSelector.activity.ImageSelectorActivity;
import com.ejz.imageSelector.bean.LocalMedia;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.AskSuccess;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.ReleaseQuestionEvent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.ui.farmland.adapter.FarmlandImagePickerAdapter;
import com.laoodao.smartagri.ui.qa.contract.AskContract;
import com.laoodao.smartagri.ui.qa.presenter.AskPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.validator.DefaultValidator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WORK on 2017/4/18.
 * 提问
 */

public class AskActivity extends BaseActivity<AskPresenter> implements AskContract.AskView {
    @Order(1)
    @NotEmpty(messageResId = R.string.ask_title)
    @BindView(R.id.et_title)
    EditText mEtTitle;
    @Order(2)
    @NotEmpty(messageResId = R.string.ask_content)
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.iv_choose_image)
    ImageView mIvChooseImage;
    @BindView(R.id.tv_longitude)
    TextView mTvLongitude;
    @BindView(R.id.tv_plant)
    TextView mTvPlant;
    @BindView(R.id.ll_crop)
    LinearLayout mLlCrop;
    private List<LocalMedia> mSelectedImageList = new ArrayList<>();
    private int maxSelectNum = 3;
    private FarmlandImagePickerAdapter mAdapter;
    private List<Plant> mSelectedPlantList = new ArrayList<>();
    private String mLatitude = "";
    private String mLongitude = "";
    private String mCity = "";
    private DefaultValidator mValidator;
    private NormalDialog dialog;
    private String mFrom;


    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ask;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerQAComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    public static void start(Context context, String from) {
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        UiUtils.startActivity(context, AskActivity.class, bundle);
    }


    @Override
    protected void configViews() {
        mFrom = getIntent().getStringExtra("from");
        mValidator = new DefaultValidator(this);
        mToolbar.setNavigationIcon(R.mipmap.ic_close);
        setSupportActionBar(mToolbar);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new FarmlandImagePickerAdapter(this, maxSelectNum, 50);
        mRecyclerview.setAdapter(mAdapter);
        mValidator.succeeded(() -> {
            commit();
        });

        RxLocation.get().locate(this)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LocationSubscriber() {
                    @Override
                    public void onLocatedSuccess(@NonNull BDLocation location) {

                        mLatitude = String.valueOf(location.getLatitude());
                        mLongitude = String.valueOf(location.getLongitude());
                        mCity = location.getCity();
                        if (mTvLongitude == null) {
                            return;
                        }
                        mTvLongitude.setText(location.getCity());
                    }

                    @Override
                    public void onLocatedFail(BDLocation bdLocation) {
                        if (mTvLongitude == null) {
                            return;
                        }
                        mTvLongitude.setText("定位失败");
                    }
                });

        dialog = new NormalDialog(this);
        dialog.dividerColor(ContextCompat.getColor(this, R.color.common_h1))
                .widthScale(0.8f)
                .style(NormalDialog.STYLE_TWO)
                .title("确定退出本次编辑")
                .titleTextSize(16)
                .titleLineColor(ContextCompat.getColor(this, R.color.white))
                .titleTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                .content("(退出后,本次编辑内容就飞走了哦！)")
                .contentTextSize(14)
                .contentTextColor(ContextCompat.getColor(this, R.color.common_h2))
                .btnText("残忍退出", "继续编辑")
                .btnTextSize(14, 14)
                .btnTextColor(ContextCompat.getColor(this, R.color.common_h2), ContextCompat.getColor(this, R.color.common_h2))
                .cornerRadius(10);
        dialog.setOnBtnClickL(() -> {
            dialog.dismiss();
            finish();
        }, () -> {
            dialog.dismiss();
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
            } else if (requestCode == CropActivity.REQUEST_CODE) {
                List<Plant> plantList = (ArrayList) data.getSerializableExtra(CropActivity.REQUEST_REUSLT);
                addPlant(plantList);
            }
        }
    }

    private void addPlant(List<Plant> plantList) {
        mSelectedPlantList.clear();
        mSelectedPlantList.addAll(plantList);
        String temp = "";
        for (Plant plant : mSelectedPlantList) {
            temp += getString(R.string.selected_plant_text, String.valueOf(plant.name));
        }
        mTvPlant.setText(temp);
    }

    private void addImage(List<LocalMedia> imageList) {
        mSelectedImageList.addAll(imageList);
        mAdapter.addAll(mSelectedImageList, true);
        boolean isVisible = mSelectedImageList.size() < maxSelectNum;
        if (isVisible) {
            visible(mIvChooseImage);
        } else {
            gone(mIvChooseImage);
        }
    }


    @Override
    public void releaseSuccess(AskSuccess success) {
        ReleaseQuestionEvent event = new ReleaseQuestionEvent();
        event.askSuccess = success;
        event.askFrom = mFrom;
        EventBus.getDefault().post(event);
        finish();
    }

    @OnClick({R.id.iv_choose_image, R.id.tv_longitude, R.id.ll_crop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_crop:
                CropActivity.start(this, mSelectedPlantList);
                break;
            case R.id.iv_choose_image:
                ImageSelectorActivity.start(this, maxSelectNum, true);
                break;
            case R.id.tv_longitude:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            dialog.show();
            return true;
        } else if (item.getItemId() == R.id.action_release) {
            mValidator.validate();
        }
        return super.onOptionsItemSelected(item);
    }

    private void commit() {
        String title = mEtTitle.getText().toString();
        String content = mEtContent.getText().toString();
        mPresenter.releaseQuetion(this, title, content, mLongitude, mLatitude, mCity, mSelectedPlantList, mSelectedImageList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_release, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        dialog.show();
    }
}