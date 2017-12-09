package com.laoodao.smartagri.ui.farmland.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.ejz.imageSelector.activity.*;
import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.bean.OperationDetail;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.event.ChoiceOperationEvent;
import com.laoodao.smartagri.event.FarmlandChangeEvent;
import com.laoodao.smartagri.ui.farmland.contract.AddOperationContract;
import com.laoodao.smartagri.ui.farmland.presenter.AddOperationPresenter;
import com.laoodao.smartagri.ui.user.adapter.ImagePickerAdapter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.wheelPicker.MechanicalOptionPicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/25.
 */

public class AddOperationActivity extends BaseActivity<AddOperationPresenter> implements AddOperationContract.AddOperationView {


    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_operation_name)
    TextView mTvOperationName;
    @BindView(R.id.btn_select)
    TextView mBtnSelect;
    @BindView(R.id.et_operation)
    EditText mEtOperation;
    @BindView(R.id.fl_other)
    FrameLayout mFlOther;
    @BindView(R.id.btn_add_farming)
    ImageView mBtnAddFarming;
    @BindView(R.id.ll_farming)
    LinearLayout mLlFarming;
    @BindView(R.id.btn_add_machine)
    ImageView mBtnAddMachine;
    @BindView(R.id.ll_machine)
    LinearLayout mLlMachine;
    @BindView(R.id.et_moeny)
    EditText mEtMoeny;
    @BindView(R.id.et_note)
    EditText mEtNote;
    @BindView(R.id.image_recyclerView)
    RecyclerView mImageRecyclerView;
    @BindView(R.id.iv_add_image)
    ImageView mIvAddImage;
    @BindView(R.id.time)
    RelativeLayout mTime;
    private String[] cropsName;
    private String[] cropsMoney;
    private String[] machineName;
    private String[] machineMoney;
    private int maxSelectNum = 3; //图片最多选择数量
    private ImagePickerAdapter mImagePickerAdapter;
    private List<LocalMedia> mSelectedImageList = new ArrayList<>();
    private int type;
    private String typeName = "";
    private int farmId;
    private int operationId;
    private int imgNum;
    private boolean isFirstNetworkImg;


    public static void start(Context context, int operationId, int farmId) {
        Bundle bundle = new Bundle();
        bundle.putInt("farmId", farmId);
        bundle.putInt("operationId", operationId);
        UiUtils.startActivity(context, AddOperationActivity.class, bundle);
    }


    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_operation;
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
        farmId = getIntent().getIntExtra("farmId", 0);
        operationId = getIntent().getIntExtra("operationId", 0);
        mBtnSelect.setText("请选择");

        initImageRecyclerView();
        if (operationId != 0) {
            initDetailData();
        } else {
            addFarming(null);
            addMachine(null);
        }
        mTvTime.setText(getTime(selectedDate.getTime()));
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        return format.format(date);
    }

    private void initDetailData() {
        mPresenter.requestOperationDetail(operationId);
    }


    @OnClick({R.id.btn_add_farming, R.id.btn_add_machine, R.id.btn_select, R.id.iv_add_image, R.id.time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_image:
                ImageSelectorActivity.start(this, maxSelectNum - mImagePickerAdapter.getCount(), true);
                break;
            case R.id.btn_add_farming:
                addFarming(null);
                break;
            case R.id.btn_add_machine:
                addMachine(null);
                break;
            case R.id.btn_select:
                UiUtils.startActivity(ChoiceOperationActivity.class);
                break;
            case R.id.time:
                initPickerView();
                break;
        }
    }

    private void addImage(List<LocalMedia> imageList) {
        mImagePickerAdapter.clear();
        mSelectedImageList.addAll(imageList);
        mImagePickerAdapter.addAll(mSelectedImageList);
        boolean isVisible = mSelectedImageList.size() < maxSelectNum;
        if (isVisible) {
            visible(mIvAddImage);
        } else {
            gone(mIvAddImage);
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


    private void getFarming() {
        int count = mLlFarming.getChildCount();
        cropsName = new String[count];
        cropsMoney = new String[count];
        for (int i = 0; i < count; i++) {
            View item = mLlFarming.getChildAt(i);
            EditText type = (EditText) item.findViewById(R.id.edittxt_type);
            EditText moeny = (EditText) item.findViewById(R.id.et_money);
            cropsName[i] = type.getText().toString();
            cropsMoney[i] = moeny.getText().toString();
        }
    }

    private void getMachine() {
        int count = mLlMachine.getChildCount();
        machineName = new String[count];
        machineMoney = new String[count];
        for (int i = 0; i < count; i++) {
            View item = mLlMachine.getChildAt(i);
            TextView type = (TextView) item.findViewById(R.id.tv_machine_type);
            EditText moeny = (EditText) item.findViewById(R.id.et_money);
            machineName[i] = type.getText() + "_" + type.getTag();
            machineMoney[i] = moeny.getText().toString();
        }
    }


    private void submit() {
        isNetworkImg();
        String time = mTvTime.getText().toString();
        String etOperation = mEtOperation.getText().toString();  //输入操作，当选择其他的时候启用
        String money = mEtMoeny.getText().toString();
        String etNote = mEtNote.getText().toString();
        if (typeName.isEmpty()) {
            UiUtils.makeText(UiUtils.getString(R.string.please_perfect_info));
            return;
        }
        getFarming();
        getMachine();
        mPresenter.requestAddOperation(this, operationId, farmId, type, typeName, etNote, time, money, cropsName, cropsMoney, machineName, machineMoney, mSelectedImageList);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ImageSelectorActivity.REQUEST_IMAGE) {
                List<LocalMedia> imageList = (ArrayList) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                addImage(imageList);
            }
        }
    }

    private void initImageRecyclerView() {
        mImagePickerAdapter = new ImagePickerAdapter(this, maxSelectNum);
        mImageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });

        mImageRecyclerView.setAdapter(mImagePickerAdapter);
        mImagePickerAdapter.setOnItemClickListener(position -> {
            com.ejz.imageSelector.activity.ImagePreviewActivity.startPreview(this, mSelectedImageList, mSelectedImageList, mSelectedImageList.size(), position, true);
        });
    }

    public void MechanicalTypeDialog(List<MechanicalType> list, TextView textView) {
        MechanicalOptionPicker picker = new MechanicalOptionPicker(this, list);
        picker.setOffset(3);
        picker.setSelectedIndex(0);
        picker.setTopLineVisible(false);
        picker.setLineVisible(false);
        picker.setTopBackgroundColor(0xFFf5f5f5);
        picker.setCancelTextColor(0xff999999);
        picker.setSubmitTextColor(0xff2AB80E);
        picker.setOnOptionPickListener(new MechanicalOptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, MechanicalType itemType) {
                textView.setText(itemType.name);
                textView.setTag(itemType.id);
            }
        });
        picker.show();
    }

    private TimePickerView pvCustomTime;
    Calendar selectedDate;

    public void initPickerView() {

        Calendar startDate = Calendar.getInstance();
        startDate.set(2000, 1, 1);
        Calendar endDate = Calendar.getInstance();

        endDate.set(2050, 12, 30);
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {

                mTvTime.setText(getTime(date));
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


    //添加机械分组
    private void addMachine(OperationDetail.MachineryCost machineryCost) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_add_machine, mLlMachine, false);
        ImageView mImgMachine = (ImageView) view.findViewById(R.id.img_machine);
        TextView tvMachineType = (TextView) view.findViewById(R.id.tv_machine_type);
        EditText etMoney = (EditText) view.findViewById(R.id.et_money);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = UiUtils.dip2px(10);
        view.setLayoutParams(params);
        //点击进行弹框选择机械种类
        view.findViewById(R.id.fl_type).setOnClickListener(v -> {
            mPresenter.requestMechanicalType(tvMachineType);
        });

        //删除组
        mImgMachine.setOnClickListener(v -> {
            mLlMachine.removeView(view);
        });

        if (machineryCost != null) {
            tvMachineType.setText(machineryCost.machineName);
            etMoney.setText(machineryCost.machineMoney);
            tvMachineType.setTag(machineryCost.id);

        }

        mLlMachine.addView(view);
        mImgMachine.setVisibility(mLlMachine.getChildCount() == 1 ? View.GONE : View.VISIBLE);

    }

    //添加农田分组
    private void addFarming(OperationDetail.CapitalCost capitalCost) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_add_farmland, mLlFarming, false);
        ImageView mImgFarmland = (ImageView) view.findViewById(R.id.img_farmland);
        EditText etType = (EditText) view.findViewById(R.id.edittxt_type);
        EditText etMoney = (EditText) view.findViewById(R.id.et_money);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = UiUtils.dip2px(10);
        view.setLayoutParams(params);
        //删除组
        mImgFarmland.setOnClickListener(v -> {
            mLlFarming.removeView(view);
        });
        if (capitalCost != null) {
            etType.setText(capitalCost.cropsName);
            etMoney.setText(capitalCost.cropsMoney);
        }
        mLlFarming.addView(view);
        mImgFarmland.setVisibility(mLlFarming.getChildCount() == 1 ? View.GONE : View.VISIBLE);

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

    @Override
    public void addOperationSuccess() {
        EventBus.getDefault().post(new FarmlandChangeEvent());
        finish();
    }

    @Override
    public void mechanicalTypeSuccess(List<MechanicalType> mechanicalTypeList, TextView view) {
        MechanicalTypeDialog(mechanicalTypeList, view);
    }

    @Override
    public void operationDetial(OperationDetail detail) {
        List<LocalMedia> selectedImageList = new ArrayList<>();
        for (String s : detail.imgarr) {
            LocalMedia local = new LocalMedia(s);
            selectedImageList.add(local);
        }
        for (OperationDetail.MachineryCost machineryCost : detail.machineryCost) {
            addMachine(machineryCost);
        }

        for (OperationDetail.CapitalCost capitalCost : detail.capitalCost) {
            addFarming(capitalCost);
        }
        addImage(selectedImageList);
        type = detail.type;
        mTvTime.setText(detail.time);
        mTvOperationName.setText(detail.typeName);
        if (Double.parseDouble(detail.artificial) == 0) {
            mEtMoeny.setHint(detail.artificial);
        } else {
            mEtMoeny.setText(detail.artificial);
        }
        mEtNote.setText(detail.remark);
        this.typeName = detail.typeName;
        mBtnSelect.setText("重新选择");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void choiceOperation(ChoiceOperationEvent event) {
        this.type = event.type;
        this.typeName = event.typeName;
        mTvOperationName.setText(typeName);
        mBtnSelect.setText("重新选择");
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }
}
