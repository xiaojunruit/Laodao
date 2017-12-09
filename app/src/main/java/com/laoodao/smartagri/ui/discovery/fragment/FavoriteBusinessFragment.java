package com.laoodao.smartagri.ui.discovery.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.bean.CottonBill;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.FavoriteBusinessAdapter;
import com.laoodao.smartagri.ui.discovery.contract.FavoriteBusinessContract;
import com.laoodao.smartagri.ui.discovery.presenter.FavoriteBusinessPresenter;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;

import java.util.Calendar;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/22.
 */

public class FavoriteBusinessFragment extends LazyFragment<FavoriteBusinessPresenter> implements FavoriteBusinessContract.FavoriteBusinessView {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_producer_name)
    TextView tvProducerName;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.tv_time_mark)
    TextView mTvTimeMark;
    private FavoriteBusinessAdapter mAdapter;
    private CottonBill.ByCompanyBean companyBean;
    private CottonBill cottonBill;

    @Override
    public void complete() {

    }

    @Override
    protected void lazyFetchData() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_favorite_business;
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
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerview.setHasFixedSize(true);
        mAdapter = new FavoriteBusinessAdapter(getContext());
        mRecyclerview.setAdapter(mAdapter);
        cottonBill = SharedPreferencesUtil.getInstance().getObject(Constant.COTTON_BILL);
        mTvTimeMark.setText("『" + cottonBill.year + "年棉花交售』");
        if (cottonBill != null) {
            mAdapter.addAll(cottonBill.byCompany);
            if (cottonBill.byCompany != null && cottonBill.byCompany.size() != 0) {
                companyBean = cottonBill.byCompany.get(0);
                tvProducerName.setText(companyBean.producer_name);
                tvDetails.setText("交售" + companyBean.sell_count + "次，总净重" + companyBean.com_net_weigh + "kg，棉花衣分平均" + companyBean.average_gin);
            }

        }

    }

}
