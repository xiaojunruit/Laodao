package com.laoodao.smartagri.ui.discovery.fragment;

import android.text.Html;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.bean.CottonBill;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.CottonBillSlideEvent;
import com.laoodao.smartagri.ui.discovery.contract.CottonFiristBillContract;
import com.laoodao.smartagri.ui.discovery.presenter.CottonFiristBillPresenter;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.TriangleView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/7/21 0021.
 */

public class CottonFiristBillFragment extends LazyFragment<CottonFiristBillPresenter> implements CottonFiristBillContract.CottonFiristBillView {
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_producer_name)
    TextView tvProducerName;
    @BindView(R.id.triangle)
    TriangleView triangle;

    //    @BindView(R.id.tv_per_year)
//    TextView tvPerYear;
//    @BindView(R.id.tv_net_weigh)
//    TextView tvNetWeigh;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.tv_by_company)
    TextView tvByCompany;
    @BindView(R.id.tv_mark)
    TextView mTvMark;
    private CottonBill cottonBill;
    String newTime;


    @Override
    public void complete() {

    }

    @Override
    protected void lazyFetchData() {


    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_cotton_firist_bill;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void CottonBillSlideChange(CottonBillSlideEvent event) {
        cottonBill = SharedPreferencesUtil.getInstance().getObject(Constant.COTTON_BILL);
        if (cottonBill != null && cottonBill.first != null) {
            triangle.setDrawText(cottonBill.first.gin_turnout, cottonBill.first.net_weigh);
            convertDate(cottonBill.first.weight_date);
            tvTime.setText("『" + newTime + "』");
            tvProducerName.setText(cottonBill.first.producer_name);
//            tvPerYear.setText(cottonBill.first.gin_turnout);
//            tvNetWeigh.setText(cottonBill.first.net_weigh);
            mTvMark.setText(Html.fromHtml(UiUtils.getString(R.string.record_mark1, cottonBill.first.type, cottonBill.first.gin_turnout, cottonBill.first.net_weigh)));
            String perYear = cottonBill.first.per_year.replace("%", "");
            tvRecord.setText(Html.fromHtml(UiUtils.getString(R.string.per_year, cottonBill.first.sell_count, cottonBill.first.weight_total, perYear)));
            tvByCompany.setText("全年在" + cottonBill.first.producer_name);
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

    @Override
    public void configViews() {

    }
}
