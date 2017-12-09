package com.laoodao.smartagri.ui.discovery.fragment;

import android.text.Html;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.bean.CottonBill;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.CottonMaxBillContract;
import com.laoodao.smartagri.ui.discovery.presenter.CottonMaxBillPresenter;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class CottonMaxBillFragment extends LazyFragment<CottonMaxBillPresenter> implements CottonMaxBillContract.CottonMaxBillView {


    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_top_net_weigh)
    TextView tvTopNetWeigh;
    @BindView(R.id.rtv_early_gin_turnout)
    RoundTextView rtvEarlyGinTurnout;
    @BindView(R.id.trv_top_gin_turnout)
    RoundTextView trvTopGinTurnout;
    @BindView(R.id.rtv_last_gin_turnout)
    RoundTextView rtvLastGinTurnout;
    @BindView(R.id.tv_early_weight_date)
    TextView tvEarlyWeightDate;
    @BindView(R.id.tv_top_weight_date)
    TextView tvTopWeightDate;
    @BindView(R.id.tv_last_weight_date)
    TextView tvLastWeightDate;
    @BindView(R.id.tv_sell_count)
    TextView tvSellCount;
    @BindView(R.id.tv_weight_total)
    TextView tvWeightTotal;
    @BindView(R.id.tv_average_gin)
    TextView tvAverageGin;
    private CottonBill cottonBill;
    private String newTime;

    @Override
    protected void lazyFetchData() {


    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_cotton_max_bill;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        init();
    }

    @Override
    public void complete() {

    }

    private void init() {
        cottonBill = SharedPreferencesUtil.getInstance().getObject(Constant.COTTON_BILL);
        if (cottonBill != null && cottonBill.now != null) {
            convertDate(cottonBill.now.top_weight_date);
            tvTime.setText("『" + newTime + "』");
            tvTopNetWeigh.setText(Html.fromHtml(getResources().getString(R.string.top_net_weigh, cottonBill.now.top_gin_turnout, cottonBill.now.top_net_weigh)));
            rtvEarlyGinTurnout.setText(cottonBill.now.early_gin_turnout);
            trvTopGinTurnout.setText(cottonBill.now.top_gin_turnout);
            rtvLastGinTurnout.setText(cottonBill.now.last_gin_turnout);
            tvEarlyWeightDate.setText(cottonBill.now.early_weight_date);
            tvTopWeightDate.setText(cottonBill.now.top_weight_date);
            tvLastWeightDate.setText(cottonBill.now.last_weight_date);
            tvSellCount.setText("全年交售棉籽" + cottonBill.now.sell_count + "次");
            tvWeightTotal.setText(UiUtils.getString(R.string.company_kg, cottonBill.now.weight_total));
            tvAverageGin.setText(cottonBill.now.average_gin);
        }
    }

    private void convertDate(String time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        SimpleDateFormat newFormatter = new SimpleDateFormat("yyyy年MM月dd", Locale.CANADA);
        try {
            Date date = formatter.parse(time);
            newTime = newFormatter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}
