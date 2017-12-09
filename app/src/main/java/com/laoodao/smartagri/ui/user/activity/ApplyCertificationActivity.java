package com.laoodao.smartagri.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ejz.multistateview.MultiStateView;
import com.flyco.roundview.RoundFrameLayout;
import com.flyco.roundview.RoundTextView;
import com.flyco.roundview.RoundViewDelegate;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.CerifyInfo;
import com.laoodao.smartagri.bean.CerifyMenu;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerUserComponent;
import com.laoodao.smartagri.ui.qa.activity.CropActivity;
import com.laoodao.smartagri.ui.user.adapter.CompanyKindAdapter;
import com.laoodao.smartagri.ui.user.adapter.CropAdapter;
import com.laoodao.smartagri.ui.user.adapter.FielAdapter;
import com.laoodao.smartagri.ui.user.contract.ApplyCertificationContract;
import com.laoodao.smartagri.ui.user.dialog.CompanyKindDialog;
import com.laoodao.smartagri.ui.user.presenter.ApplyCertificationPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.UploadSinglePicture;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ApplyCertificationActivity extends BaseActivity<ApplyCertificationPresenter> implements ApplyCertificationContract.ApplyCertificationView {


    @BindView(R.id.rb_farmer)
    RadioButton mRbFarmer;
    @BindView(R.id.rb_shop)
    RadioButton mRbShop;
    @BindView(R.id.rb_expert)
    RadioButton mRbExpert;
    @BindView(R.id.rtv_add_farmer)
    RoundTextView mRtvAddFarmer;
    @BindView(R.id.fl_nature)
    FrameLayout mFlNature;
    @BindView(R.id.rtv_add_expert_img)
    RoundFrameLayout mRtvAddExpertImg;
    @BindView(R.id.field_recycler)
    RecyclerView mFieldRecycler;
    @BindView(R.id.crop_recycler)
    RecyclerView mCropRecycler;
    @BindView(R.id.rtv_add_crop)
    RoundFrameLayout mRtvAddCrop;

    @BindView(R.id.btn_commit)
    RoundTextView mBtnCommit;
    @BindView(R.id.fl_farmer_view)
    FrameLayout mFlFarmerView;
    @BindView(R.id.fl_shop_view)
    FrameLayout mFlShopView;
    @BindView(R.id.fl_expert_view)
    FrameLayout mFlExpertView;
    @BindView(R.id.ll_item_farmer_parent)
    LinearLayout mLlItemFarmerParent;
    @BindView(R.id.tv_nature)
    TextView mTvNature;
    @BindView(R.id.et_true_name)
    EditText mEtTrueName;
    @BindView(R.id.iv_license)
    ImageView mIvLicense;
    @BindView(R.id.iv_id_card)
    ImageView mIvIdCard;

    @BindView(R.id.fl_fail_reason)
    FrameLayout mFlFailReason;
    @BindView(R.id.tv_fail_reason)
    TextView mTvFailReason;
    @BindView(R.id.rvt_second)
    RoundTextView mRvtSecond;
    @BindView(R.id.rtv_three)
    RoundTextView mRtvThree;
    @BindView(R.id.iv_pass)
    ImageView mIvPass;
    @BindView(R.id.rtv_add_identity_certificate)
    RoundFrameLayout mRtvAddIdentityCertificate;
    @BindView(R.id.btv_isInput)
    Button mBtvIsInput;
    @BindView(R.id.tv_second)
    TextView mTvSecond;
    @BindView(R.id.tv_three)
    TextView mTvThree;
    @BindView(R.id.view_reason)
    View mViewReason;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.tv_true_name)
    TextView mTvTrueName;
    @BindView(R.id.tv_identity)
    TextView mTvIdentity;
    @BindView(R.id.tv_enterprise_nature)
    TextView mTvEnterpriseNature;
    @BindView(R.id.tv_license)
    TextView mTvLicense;
    @BindView(R.id.tv_field)
    TextView mTvField;
    @BindView(R.id.tv_crop)
    TextView mTvCrop;
    @BindView(R.id.tv_id_card)
    TextView mTvIdCard;
    private CompanyKindAdapter mKindAdapter;
    private CompanyKindDialog mKindDialog;
    private CropAdapter mCropAdapter;

    private UploadSinglePicture mUploadSinglePicture;
    private FielAdapter mFielAdapter;
    private String[] cropsName, cropsAcreage;
    private File shopFile = new File(""), expertFile = new File("");
    public CerifyInfo mInfo;
    private int mMemberType;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerUserComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    public static void start(Context context, int memberType) {
        Bundle bundle = new Bundle();
        bundle.putInt("memberType", memberType);
        UiUtils.startActivity(context, ApplyCertificationActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_apply_certification;
    }

    @Override
    protected void configViews() {
        initText();
        mMemberType = getIntent().getIntExtra("memberType", 0);
        mFieldRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        mFielAdapter = new FielAdapter(this, position -> {
            mFielAdapter.getItem(position).isSelect = !mFielAdapter.getItem(position).isSelect;
            mFielAdapter.notifyDataSetChanged();
        });
        mFieldRecycler.setAdapter(mFielAdapter);
        mCropRecycler.setLayoutManager(new GridLayoutManager(this, 4));
        mCropAdapter = new CropAdapter(this);
        mCropRecycler.setAdapter(mCropAdapter);
        DividerDecoration decoration = new DividerDecoration(ContextCompat.getColor(this, R.color.aply_gray), UiUtils.dip2px(10), 0, 0);
        mCropRecycler.addItemDecoration(decoration);

        mCropRecycler.setNestedScrollingEnabled(false);
        mFieldRecycler.setNestedScrollingEnabled(false);
        mUploadSinglePicture = new UploadSinglePicture(this, false);
        if (mMemberType == 0) {
            addFarmerItem(null, 0);
            addFarmerItem(null, 0);
        }
        mUploadSinglePicture.setOnUploadListener(file -> {
            if (mRbShop.isChecked()) {
                shopFile = file;
                Glide.with(this).load(file).into(mIvLicense);
            } else if (mRbExpert.isChecked()) {
                expertFile = file;
                Glide.with(this).load(file).into(mIvIdCard);
            }

        });
        mKindAdapter = new CompanyKindAdapter(this, poistoin -> {
            for (int i = 0; i < mKindAdapter.getAllData().size(); i++) {
                if (i == poistoin) {
                    mKindAdapter.getItem(i).isSelect = true;
                } else {
                    mKindAdapter.getItem(i).isSelect = false;
                }
            }
            mKindAdapter.notifyDataSetChanged();
            mKindDialog.dismiss();
            mTvNature.setText(mKindAdapter.getItem(poistoin).name);
            mTvNature.setTag(mKindAdapter.getItem(poistoin).id);
        });
        mPresenter.requestMenu();
    }

    private void initText() {
        mTvTrueName.setText(Html.fromHtml(getResources().getString(R.string.true_name)));
        mTvIdentity.setText(Html.fromHtml(getResources().getString(R.string.identity)));
        mTvEnterpriseNature.setText(Html.fromHtml(getResources().getString(R.string.enterprise_nature)));
        mTvLicense.setText(Html.fromHtml(getResources().getString(R.string.license)));
        mTvField.setText(Html.fromHtml(getResources().getString(R.string.field)));
        mTvCrop.setText(Html.fromHtml(getResources().getString(R.string.crop)));
        mTvIdCard.setText(Html.fromHtml(getResources().getString(R.string.id_card)));
    }

    @Override
    public void complete() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUploadSinglePicture.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CropActivity.REQUEST_CODE) {
                List<Plant> plantList = (List<Plant>) data.getSerializableExtra(CropActivity.REQUEST_REUSLT);
                mCropAdapter.addAll(plantList, true);
            }
        }

    }

    @OnClick({R.id.rb_farmer, R.id.rb_shop, R.id.rb_expert, R.id.rtv_add_farmer, R.id.rtv_add_expert_img, R.id.rtv_add_crop, R.id.rtv_add_identity_certificate, R.id.btn_commit, R.id.fl_nature})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_farmer:
                showOrGone(mFlFarmerView, mFlShopView, mFlExpertView);
                break;
            case R.id.rb_shop:
                showOrGone(mFlShopView, mFlFarmerView, mFlExpertView);
                break;
            case R.id.rb_expert:
                showOrGone(mFlExpertView, mFlFarmerView, mFlShopView);
                break;
            case R.id.rtv_add_farmer:
                addFarmerItem(null, 0);
                break;
            case R.id.rtv_add_expert_img:
                mUploadSinglePicture.avatarShareDialog();
                break;
            case R.id.rtv_add_crop:
                List<Plant> followPlant = mCropAdapter.getAllData();
                CropActivity.start(this, 0, followPlant, false);
                break;
            case R.id.rtv_add_identity_certificate:
                mUploadSinglePicture.avatarShareDialog();
                break;
            case R.id.btn_commit:

                submit();
                break;
            case R.id.fl_nature:
                mKindDialog.show();
                break;
        }
    }

    public void submit() {
        User user = Global.getInstance().getUserInfo();
        if (TextUtils.isEmpty(mEtTrueName.getText().toString())) {
            Toast.makeText(this, "请选填写真实姓名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mRbFarmer.getTag() == null || mRbShop.getTag() == null || mRbExpert.getTag() == null) {
            Toast.makeText(this, "系统出错", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mRbFarmer.isChecked()) {
            getCrop();
            if (cropsName.length == 0) {
                Toast.makeText(this, "请填写种植作物", Toast.LENGTH_SHORT).show();
                return;
            }
            if (cropsAcreage.length == 0) {
                Toast.makeText(this, "请填写种植面积", Toast.LENGTH_SHORT).show();
                return;
            }
            if (cropsName.length != cropsAcreage.length) {
                Toast.makeText(this, "信息不完整", Toast.LENGTH_SHORT).show();
                return;
            }
            mPresenter.addCerify(user.uid, mRbFarmer.getTag().toString(), mEtTrueName.getText().toString(), this, cropsName, cropsAcreage);
        } else if (mRbShop.isChecked()) {
            String kindType = "";
            if (mTvNature.getTag() != null) {
                kindType = mTvNature.getTag().toString();
            }
            if (mInfo != null && mInfo.tradePic != null) {
                if (TextUtils.isEmpty(mInfo.tradePic.get(0)) && TextUtils.isEmpty(shopFile.getPath())) {
                    Toast.makeText(this, "请选上传营业执照照片", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                if (TextUtils.isEmpty(shopFile.getPath())) {
                    Toast.makeText(this, "请选上传营业执照照片", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            if (TextUtils.isEmpty(kindType)) {
                Toast.makeText(this, "请选择企业性质", Toast.LENGTH_SHORT).show();
                return;
            }
            mPresenter.addCerify(user.uid, mRbShop.getTag().toString(), mEtTrueName.getText().toString(), this, shopFile, kindType);
        } else if (mRbExpert.isChecked()) {
            String fieldType = "";
            for (int i = 0; i < mFielAdapter.getAllData().size(); i++) {
                if (mFielAdapter.getItem(i).isSelect) {
                    fieldType += mFielAdapter.getItem(i).id + ",";
                }
            }
            String crops = "";
            for (int i = 0; i < mCropAdapter.getAllData().size(); i++) {
                crops += mCropAdapter.getItem(i).id + ",";
            }
            if (TextUtils.isEmpty(fieldType)) {
                Toast.makeText(this, "请选择擅长领域", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(crops)) {
                Toast.makeText(this, "请选择擅长作物", Toast.LENGTH_SHORT).show();
                return;
            }
            if (mInfo != null && mInfo.idenCardPic != null) {
                if (TextUtils.isEmpty(mInfo.idenCardPic.get(0)) && TextUtils.isEmpty(expertFile.getPath())) {
                    Toast.makeText(this, "请选上传身份证件照片", Toast.LENGTH_SHORT).show();
                    return;
                }
            } else {
                if (TextUtils.isEmpty(expertFile.getPath())) {
                    Toast.makeText(this, "请选上传身份证件照片", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            mPresenter.addCerify(user.uid, mRbExpert.getTag().toString(), mEtTrueName.getText().toString(), this, fieldType, crops, expertFile);
        }
    }

    public void showOrGone(FrameLayout v1, FrameLayout v2, FrameLayout v3) {
        v1.setVisibility(View.VISIBLE);
        v2.setVisibility(View.GONE);
        v3.setVisibility(View.GONE);
    }

    public void addFarmerItem(CerifyInfo.cropInfoBean info, int state) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_certification_farmer, mLlItemFarmerParent, false);
        RoundTextView roundTextView = (RoundTextView) view.findViewById(R.id.rtv_delete);
        EditText name = (EditText) view.findViewById(R.id.et_crop);
        EditText acreage = (EditText) view.findViewById(R.id.et_area);
        roundTextView.setOnClickListener(v -> {
            mLlItemFarmerParent.removeView(view);
        });
        if (info != null) {
            acreage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView textView = (TextView) view.findViewById(R.id.tv_company);
            textView.setVisibility(View.VISIBLE);
            name.setText(info.cropsName);
            acreage.setText(info.cropsAcreage);

        }
        if (state == 2 || state == 0) {
            roundTextView.setVisibility(View.VISIBLE);
        }
        mLlItemFarmerParent.addView(view);
    }


    public void getCrop() {
        int count = mLlItemFarmerParent.getChildCount();
        List<String> listName = new ArrayList<>();
        List<String> listAcreage = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View item = mLlItemFarmerParent.getChildAt(i);
            EditText name = (EditText) item.findViewById(R.id.et_crop);
            EditText acreage = (EditText) item.findViewById(R.id.et_area);
            if (!TextUtils.isEmpty(name.getText().toString())) {
                listName.add(name.getText().toString());
            }
            if (!TextUtils.isEmpty(acreage.getText().toString())) {
                listAcreage.add(acreage.getText().toString());
            }

        }
        cropsName = new String[listName.size()];
        cropsName = listName.toArray(cropsName);
        cropsAcreage = new String[listAcreage.size()];
        cropsAcreage = listAcreage.toArray(cropsAcreage);
    }


    @Override
    public void menuSuccess(CerifyMenu menu) {
        User user = Global.getInstance().getUserInfo();
        if (mMemberType != 0) {
            mPresenter.requestInfo(user.uid);
        } else {
            if (menu != null) mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            else {
                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
                return;
            }
        }
        mRbFarmer.setText(menu.cerifyType.get(0).name);
        mRbFarmer.setTag(menu.cerifyType.get(0).id);

        mRbShop.setText(menu.cerifyType.get(1).name);
        mRbShop.setTag(menu.cerifyType.get(1).id);

        mRbExpert.setText(menu.cerifyType.get(2).name);
        mRbExpert.setTag(menu.cerifyType.get(2).id);

        mFielAdapter.addAll(menu.specialField, true);
        mKindAdapter.addAll(menu.companyKind, true);
        mKindDialog = new CompanyKindDialog(this, mKindAdapter);
    }


    @Override
    public void infoSuccess(CerifyInfo info) {
        mInfo = info;
        if (info != null) {
            if (info.cerifyType != 1) {
                addFarmerItem(null, 0);
                addFarmerItem(null, 0);
            }
            if (info.verify == 1 || info.verify == 3) {
                mFlFailReason.setVisibility(View.GONE);
                mBtvIsInput.setVisibility(View.VISIBLE);
                mBtnCommit.setVisibility(View.GONE);
                mRtvAddFarmer.setVisibility(View.GONE);
                mRtvAddExpertImg.setVisibility(View.GONE);
                mRtvAddCrop.setVisibility(View.GONE);
                mRtvAddIdentityCertificate.setVisibility(View.GONE);
                mViewReason.setVisibility(View.GONE);
            } else {
                mViewReason.setVisibility(View.VISIBLE);
                mFlFailReason.setVisibility(View.VISIBLE);
                mBtvIsInput.setVisibility(View.GONE);
                mTvFailReason.setText("      " + info.failReason);
            }
            if (info.verify == 1) {
                RoundViewDelegate delegate = mRvtSecond.getDelegate();
                delegate.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
                mTvSecond.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            } else if (info.verify == 2) {
                RoundViewDelegate delegate = mRvtSecond.getDelegate();
                delegate.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
                mTvSecond.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            } else if (info.verify == 3) {
                RoundViewDelegate delegate = mRvtSecond.getDelegate();
                delegate.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
                RoundViewDelegate delegate1 = mRtvThree.getDelegate();
                delegate1.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
                mTvSecond.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                mTvThree.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                mIvPass.setVisibility(View.VISIBLE);
            }
            mEtTrueName.setText(info.trueName);

            if (info.cerifyType == Integer.parseInt(mRbFarmer.getTag().toString())) {
                mRbFarmer.setChecked(true);
                showOrGone(mFlFarmerView, mFlShopView, mFlExpertView);
                for (CerifyInfo.cropInfoBean cropInfoBean : info.cropInfo) {
                    addFarmerItem(cropInfoBean, info.verify);
                }
            } else if (info.cerifyType == Integer.parseInt(mRbShop.getTag().toString())) {
                mRbShop.setChecked(true);
                showOrGone(mFlShopView, mFlFarmerView, mFlExpertView);
                for (int i = 0; i < mKindAdapter.getAllData().size(); i++) {
                    if (info.companyKindType == mKindAdapter.getItem(i).id) {
                        mTvNature.setText(mKindAdapter.getItem(i).name);
                    }
                }
                mTvNature.setTag(info.companyKindType);
                if (info.tradePic != null) {
                    Glide.with(this).load(info.tradePic.get(0)).into(mIvLicense);
                }
                mTvNature.setCompoundDrawables(null, null, null, null);

            } else if (info.cerifyType == Integer.parseInt(mRbExpert.getTag().toString())) {
                mRbExpert.setChecked(true);
                showOrGone(mFlExpertView, mFlFarmerView, mFlShopView);
                for (int i = 0; i < mFielAdapter.getAllData().size(); i++) {
                    for (int j = 0; j < info.specialFieldType.size(); j++) {
                        if (info.specialFieldType.get(j) == mFielAdapter.getItem(i).id) {
                            mFielAdapter.getItem(i).isSelect = true;
                        }
                    }
                }
                mFielAdapter.notifyDataSetChanged();
                List<Plant> plants = new ArrayList<>();
                Plant plant;
                for (int i = 0; i < info.goodatCrops.size(); i++) {
                    plant = new Plant();
                    plant.id = info.goodatCrops.get(i).id;
                    plant.name = info.goodatCrops.get(i).name;
                    plants.add(plant);
                }
                mCropAdapter.addAll(plants, true);
                if (info.idenCardPic != null) {
                    Glide.with(this).load(info.idenCardPic.get(0)).into(mIvIdCard);
                }
            }
        }
    }

    @Override
    public void addSuccess() {
        User user = Global.getInstance().getUserInfo();
        user.memberType = "2";
        user.memberTypeName1 = "认证审核中";
        user.truename = mEtTrueName.getText().toString();
        Global.getInstance().setUserInfo(user);

        mBtvIsInput.setVisibility(View.VISIBLE);
        mFlFailReason.setVisibility(View.GONE);
        if (mRbFarmer.isChecked()) {
            int count = mLlItemFarmerParent.getChildCount();
            for (int i = 0; i < count; i++) {
                View item = mLlItemFarmerParent.getChildAt(i);
                RoundTextView roundTextView = (RoundTextView) item.findViewById(R.id.rtv_delete);
                roundTextView.setVisibility(View.GONE);
                EditText name = (EditText) item.findViewById(R.id.et_crop);
                EditText acreage = (EditText) item.findViewById(R.id.et_area);
                if (TextUtils.isEmpty(name.getText()) && TextUtils.isEmpty(acreage.getText())) {
                    item.setVisibility(View.GONE);
                }
            }
            mRtvAddFarmer.setVisibility(View.GONE);
        } else if (mRbShop.isChecked()) {
            mRtvAddExpertImg.setVisibility(View.GONE);

        } else if (mRbExpert.isChecked()) {
            mRtvAddCrop.setVisibility(View.GONE);
            mRtvAddIdentityCertificate.setVisibility(View.GONE);
        }
        RoundViewDelegate delegate = mRvtSecond.getDelegate();
        delegate.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        mTvSecond.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        mBtvIsInput.setVisibility(View.VISIBLE);
        mBtnCommit.setVisibility(View.GONE);
        mBtnCommit.setFocusable(true);
        mBtnCommit.setFocusableInTouchMode(true);
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