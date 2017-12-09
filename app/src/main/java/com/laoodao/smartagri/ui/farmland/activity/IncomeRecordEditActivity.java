package com.laoodao.smartagri.ui.farmland.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.ejz.imageSelector.activity.ImagePreviewActivity;
import com.ejz.imageSelector.activity.ImageSelectorActivity;
import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.bean.base.AccountDetail;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.event.BillChangeEvent;
import com.laoodao.smartagri.ui.farmland.adapter.FarmlandImagePickerAdapter;
import com.laoodao.smartagri.ui.farmland.adapter.IncomeRecordAdapter;
import com.laoodao.smartagri.ui.farmland.contract.IncomeRecordContract;
import com.laoodao.smartagri.ui.farmland.presenter.IncomeRecordPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/27.
 */

public class IncomeRecordEditActivity extends BaseActivity<IncomeRecordPresenter> implements IncomeRecordContract.FarmIncomeView {
    @BindView(R.id.tv_type)
    TextView mTvType;
    @BindView(R.id.iv_type)
    ImageView mIvType;
    @BindView(R.id.tv_calendar)
    TextView mTvCalendar;
    @BindView(R.id.img_recyclerview)
    RecyclerView mImgRecyclerview;
    @BindView(R.id.iv_choose_image)
    ImageView mIvChooseImage;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.et_remark)
    EditText mEtRemark;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private FarmlandImagePickerAdapter mImageAdapter;
    private int maxSelectNum = 3;
    private List<LocalMedia> mSelectedImageList = new ArrayList<>();
    private int typeId;
    private boolean isFirstNetworkImg;
    private int imgNum;
    private AccountDetail detail;
    private int id;
    private IncomeRecordAdapter mAdapter;

    private TimePickerView pvCustomTime;
    Calendar selectedDate;

    public static void start(Context context, AccountDetail detail, int id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", detail);
        bundle.putInt("id", id);
        UiUtils.startActivity(context, IncomeRecordEditActivity.class, bundle);
    }

    @Override
    public void complete() {

    }

    @Override
    public void initData(List<RecordClassification> recordClassifications) {
        for (int i = 0; i < recordClassifications.size(); i++) {
            if (recordClassifications.get(i).id == detail.operateType) {
                recordClassifications.get(i).isSelect = true;
            }
        }
        mAdapter.addAll(recordClassifications);
    }

    @Override
    public void accountingSuccess() {
        EventBus.getDefault().post(new BillChangeEvent());
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_income_record_edit;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        selectedDate = Calendar.getInstance();
        mAdapter = new IncomeRecordAdapter(this);
        detail = (AccountDetail) getIntent().getSerializableExtra("detail");
        id = getIntent().getIntExtra("id", 0);
        mImageAdapter = new FarmlandImagePickerAdapter(this, maxSelectNum);
        mImgRecyclerview.setAdapter(mImageAdapter);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 5));
        mRecyclerview.setAdapter(mAdapter);
        mImgRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        mPresenter.requestDate(detail.accountType);
        mAdapter.setOnItemClickListener(position -> {
            typeId = mAdapter.getItem(position).id;
            mTvType.setText(mAdapter.getItem(position).title);
            Glide.with(this)
                    .load(mAdapter.getItem(position).icon)
                    .into(mIvType);
            for (int i = 0; i < mAdapter.getItemCount(); i++) {
                if (i != position) {
                    mAdapter.getAllData().get(i).isSelect = false;
                }
            }
            mAdapter.getItem(position).isSelect = true;
            mAdapter.notifyDataSetChanged();
        });
        mImageAdapter.setOnItemClickListener(position -> {
            ImagePreviewActivity.startPreview(this, mSelectedImageList, mSelectedImageList, mSelectedImageList.size(), position, true);
        });
        bindData(detail);
        mTvCalendar.setText(getTime(selectedDate.getTime()));
    }


    private void bindData(AccountDetail detail) {
        if (!TextUtils.isEmpty(detail.icon)) {
            Glide.with(this).load(detail.icon).into(mIvType);
        }
        mTvType.setText(detail.operateName);
        mEtMoney.setText(detail.account);
        mEtRemark.setText(detail.remark);
        typeId = detail.operateType;
        setTitle(detail.accountType == Constant.EXPENDITURE ? "支出" : "收入");
        List<LocalMedia> selectImg = new ArrayList<>();
        for (String s : detail.imgarr) {
            LocalMedia localMedia = new LocalMedia(s);
            selectImg.add(localMedia);
        }
        addImage(selectImg);
    }


    @OnClick({R.id.tv_calendar, R.id.iv_choose_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_image:
                ImageSelectorActivity.start(this, maxSelectNum - mImageAdapter.getItemCount(), true);
                break;
            case R.id.tv_calendar:
                initPickerView();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
        mImageAdapter.clear();
        mSelectedImageList.addAll(imageList);
        mImageAdapter.addAll(mSelectedImageList);
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

    public void submit() {
        isNetworkImg();
        String money = mEtMoney.getText().toString();
        String remark = mEtRemark.getText().toString();
        String time = mTvCalendar.getText().toString();
        if (TextUtils.isEmpty(money)) {
            UiUtils.makeText("请填写金额");
            return;
        }
        if (TextUtils.isEmpty(remark)) {
            UiUtils.makeText("请输入备注信息");
            return;
        }
        mPresenter.requestAccounting(this, id, typeId, detail.accountType, money, remark, time, mSelectedImageList);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_complete) {
            submit();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete, menu);
        return true;
    }

    public void initPickerView() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 1, 1);
        Calendar endDate = Calendar.getInstance();

        endDate.set(2050, 12, 30);
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

                mTvCalendar.setText(getTime(date));
            }
        }).setDate(selectedDate)
                .setDividerColor(R.color.common_divider_narrow)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.time_picker_view, v -> {
                    TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                    TextView tvCancel = (TextView) v.findViewById(R.id.tv_cancel);

                    tvCancel.setOnClickListener(v1 -> {
                        pvCustomTime.dismiss();
                    });
                    tvSubmit.setOnClickListener(v1 -> {
                        pvCustomTime.returnData();
                    });
                })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setLineSpacingMultiplier(2.0f)
                .isCenterLabel(false)
                .setOutSideCancelable(false)
                .isCyclic(true)
                .build();
        pvCustomTime.show();
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        return format.format(date);
    }
}
