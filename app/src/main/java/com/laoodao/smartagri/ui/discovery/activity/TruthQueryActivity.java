package com.laoodao.smartagri.ui.discovery.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.flyco.roundview.RoundLinearLayout;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.TruthQueryContract;
import com.laoodao.smartagri.ui.discovery.presenter.TruthQueryPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/21.
 */

public class TruthQueryActivity extends BaseActivity<TruthQueryPresenter> implements TruthQueryContract.TruthQueryView {
    @BindView(R.id.ll_pesticides)
    LinearLayout mLlPesticides;
    @BindView(R.id.ll_fertilizer)
    LinearLayout mLlFertilizer;
    @BindView(R.id.ll_seed)
    LinearLayout mLlSeed;
    @BindView(R.id.ll_microbial_fertilizer)
    LinearLayout mLlMicrobialFertilizer;
    @BindView(R.id.et_number)
    EditText mEtNumber;
    @BindView(R.id.et_company_name)
    EditText mEtCompanyName;
    @BindView(R.id.etactive_ingredient)
    EditText mEtactiveIngredient;
    @BindView(R.id.et_crop)
    EditText mEtCrop;
    @BindView(R.id.search)
    RoundLinearLayout mSearch;

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_truth_query;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        mLlPesticides.setSelected(true);
    }

    @OnClick({R.id.ll_pesticides, R.id.ll_fertilizer, R.id.ll_seed, R.id.ll_microbial_fertilizer, R.id.tv_submit, R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_pesticides:
                setTextHint("登记证号", "企业名称", "有效成分", "防治作物", false);
                isSelected(true, false, false, false);
                break;
            case R.id.ll_fertilizer:
                setTextHint("登记证号", "通用名称", "", "企业名称", true);
                isSelected(false, true, false, false);
                break;
            case R.id.ll_seed:
                setTextHint("审定编号", "品种名称", "", "作物种类", true);
                isSelected(false, false, true, false);
                break;
            case R.id.ll_microbial_fertilizer:
                setTextHint("登记证号", "通用名称", "", "企业名称", true);
                isSelected(false, false, false, true);
                break;
            case R.id.tv_submit:
                submit();
                break;
            case R.id.search:
                UiUtils.startActivity(TruthQuerySearchActivity.class);
                break;
        }
    }


    public void submit() {
//        String url = "";
//        if (mLlPesticides.isSelected()) { //农药表单提交
//            String num = mEtNumber.getText().toString();
//            String manufacturer = mEtCompanyName.getText().toString();
//            String active_principle = mEtactiveIngredient.getText().toString();
//            String dose = mEtCrop.getText().toString();
//            url = Constant.WEB_SHOP_SITE + "found/zw/pesticide_list.shtml?number=" + num + "&active_principle=" + active_principle + "&dose=" + dose + "&manufacturer=" + manufacturer;
//        } else if (mLlFertilizer.isSelected()) {//肥料表单提交
//            String num = mEtNumber.getText().toString();
//            String commonName = mEtCompanyName.getText().toString();
//            String company = mEtCrop.getText().toString();
//            url = Constant.WEB_SHOP_SITE + "found/zw/fertilizer_list.shtml?number=" + num + "&common_name=" + commonName + "&company=" + company;
//        } else if (mLlSeed.isSelected()) { //种子表单提交
//            String num = mEtNumber.getText().toString();
//            String variety = mEtCompanyName.getText().toString();
//            String category = mEtCrop.getText().toString();
//            url = Constant.WEB_SHOP_SITE + "found/zw/seed_list.shtml?number=" + num + "&variety=" + variety + "&category=" + category;
//            WebViewActivity.start(this, url);
//        } else if (mLlMicrobialFertilizer.isSelected()) { //微生物肥料
//            String num = mEtNumber.getText().toString();
//            String commonName = mEtCompanyName.getText().toString();
//            String company = mEtCrop.getText().toString();
//            url = Constant.WEB_SHOP_SITE + "found/zw/microbialfertilizer_list.shtml?number=" + num + "&common_name=" + commonName + "&company=" + company;
//        }
//        WebViewActivity.start(this, url);
        if (mLlPesticides.isSelected()) {//农药表单提交
            String num = mEtNumber.getText().toString();
            String manufacturer = mEtCompanyName.getText().toString();
            String active_principle = mEtactiveIngredient.getText().toString();
            String dose = mEtCrop.getText().toString();
            PesticideActivity.start(this, num, active_principle, dose, manufacturer);
        } else if (mLlFertilizer.isSelected()) {//肥料表单提交
            String num = mEtNumber.getText().toString();
            String commonName = mEtCompanyName.getText().toString();
            String company = mEtCrop.getText().toString();
            QueryFertilizerActivity.start(this, num, commonName, company);
        } else if (mLlSeed.isSelected()) { //种子表单提交
            String num = mEtNumber.getText().toString();
            String variety = mEtCompanyName.getText().toString();
            String category = mEtCrop.getText().toString();
            QuerySeedActivity.start(this, num, category, variety);
        } else if (mLlMicrobialFertilizer.isSelected()) { //微生物肥料
            String num = mEtNumber.getText().toString();
            String commonName = mEtCompanyName.getText().toString();
            String company = mEtCrop.getText().toString();
            MicrobialFertilizerActivity.start(this, num, commonName, company);
        }

    }


    public void setTextHint(String Number, String CompanyName, String Ingredient, String Crop, boolean isVisibility) {
        mEtNumber.setHint(Number);
        mEtCompanyName.setHint(CompanyName);
        mEtactiveIngredient.setHint(Ingredient);
        mEtCrop.setHint(Crop);
        mEtactiveIngredient.setVisibility(isVisibility ? View.GONE : View.VISIBLE);
    }

    public void isSelected(boolean pesticides, boolean fertilizer, boolean seed, boolean microbialFertilizer) {
        mLlPesticides.setSelected(pesticides);
        mLlFertilizer.setSelected(fertilizer);
        mLlSeed.setSelected(seed);
        mLlMicrobialFertilizer.setSelected(microbialFertilizer);
    }
}
