package com.laoodao.smartagri.ui.farmland.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.event.BillChangeEvent;
import com.laoodao.smartagri.event.BooksOperationEvent;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/4/23.
 */

public class IncomeRecordFragment extends BaseFragment<IncomeRecordPresenter> implements IncomeRecordContract.FarmIncomeView {
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
    private TimePickerView pvCustomTime;
    private int mType = 1;
    private int typeId;
    Calendar selectedDate;
    private IncomeRecordAdapter mAdapter;

    @Override
    public void complete() {

    }

    public static IncomeRecordFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("type", id);
        IncomeRecordFragment fragment = new IncomeRecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_income_record;
    }


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        mAdapter = new IncomeRecordAdapter(getContext());
        selectedDate = Calendar.getInstance();
        Bundle args = getArguments();
        if (args != null) {
            mType = args.getInt("type");
        }
        mImageAdapter = new FarmlandImagePickerAdapter(getContext(), maxSelectNum);
        mImgRecyclerview.setAdapter(mImageAdapter);
        mRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 5));
        mRecyclerview.setAdapter(mAdapter);
        mImgRecyclerview.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        mPresenter.requestDate(mType);
        mAdapter.setOnItemClickListener(position -> {
            typeId = mAdapter.getItem(position).id;
            mTvType.setText(mAdapter.getItem(position).title);
            Glide.with(getContext())
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
            ImagePreviewActivity.startPreview(getActivity(), mSelectedImageList, mSelectedImageList, mSelectedImageList.size(), position, true);
        });
        mTvCalendar.setText(getTime(selectedDate.getTime()));
    }

    public void initPickerView() {
        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 1, 1);
        Calendar endDate = Calendar.getInstance();

        endDate.set(2050, 12, 30);
        pvCustomTime = new TimePickerView.Builder(getContext(), new TimePickerView.OnTimeSelectListener() {
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

    @OnClick({R.id.tv_calendar, R.id.iv_choose_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_choose_image:
                ImageSelectorActivity.start(getActivity(), maxSelectNum - mImageAdapter.getItemCount(), true);
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
        mSelectedImageList.addAll(imageList);
        mImageAdapter.clear();
        mImageAdapter.addAll(mSelectedImageList);
        boolean isVisible = mSelectedImageList.size() < maxSelectNum;
        if (isVisible) {
            visible(mIvChooseImage);
        } else {
            gone(mIvChooseImage);
        }
    }


    @Override
    public void initData(List<RecordClassification> recordClassifications) {
        recordClassifications.get(0).isSelect=true;
        mAdapter.addAll(recordClassifications);
        if (recordClassifications.size() == 0) {
            return;
        }
        typeId = recordClassifications.get(0).id;
        mTvType.setText(recordClassifications.get(0).title);
        Glide.with(getContext())
                .load(recordClassifications.get(0).icon)
                .into(mIvType);

    }

    @Override
    public void accountingSuccess() {
        EventBus.getDefault().post(new BillChangeEvent());
        getActivity().finish();
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        return format.format(date);
    }


    public void submit() {
        String money = mEtMoney.getText().toString();
        String remark = mEtRemark.getText().toString();
        String time = mTvCalendar.getText().toString();
        mPresenter.requestAccounting(getActivity(), 0, typeId, mType, money, remark, time, mSelectedImageList);
    }
}


