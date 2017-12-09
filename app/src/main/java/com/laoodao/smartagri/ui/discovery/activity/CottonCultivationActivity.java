package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.cotton.Cotton;
import com.laoodao.smartagri.bean.cotton.CottonJsxxList;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.CottonRefreshCode;
import com.laoodao.smartagri.ui.discovery.adapter.CottonCultivationAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.CottonJsxxListAdapter;
import com.laoodao.smartagri.ui.discovery.contract.CottonCultivationContract;
import com.laoodao.smartagri.ui.discovery.presenter.CottonCultivationPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.decoration.DividerDecoration;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/6/30.
 */

public class CottonCultivationActivity extends BaseActivity<CottonCultivationPresenter> implements CottonCultivationContract.CottonCultivationQueryView {


    @BindView(R.id.cotton_recyclerview)
    RecyclerView mCottonRecyclerview;
    @BindView(R.id.recyclerview_list)
    RecyclerView mRecyclerViewList;
    public CottonCultivationAdapter cultivationAdapter;
    @BindView(R.id.fl_first_title)
    FrameLayout flFirstTitle;
    @BindView(R.id.fl_two_title)
    FrameLayout flTwoTitle;
    @BindView(R.id.recyclerview_end0131)
    RecyclerView recyclerviewEnd0131;
    @BindView(R.id.ll_list)
    LinearLayout llList;
    @BindView(R.id.ll_end0131)
    LinearLayout llEnd0131;
    @BindView(R.id.recyclerview_End31)
    RecyclerView recyclerviewEnd31;
    @BindView(R.id.ll_End31)
    LinearLayout llEnd31;
    @BindView(R.id.fl_three_title)
    FrameLayout flThreeTitle;
    @BindView(R.id.tv_list_sell)
    TextView tvListSell;
    @BindView(R.id.tv_End31_sell)
    TextView tvEnd31Sell;
    @BindView(R.id.tv_end0131_sell)
    TextView tvEnd0131Sell;
    @BindView(R.id.iv_first)
    ImageView ivFirst;
    @BindView(R.id.iv_two)
    ImageView ivTwo;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.tv_first)
    TextView tvFirst;
    @BindView(R.id.tv_two)
    TextView tvTwo;
    @BindView(R.id.tv_three)
    TextView tvThree;
    String mData = "";

    private Cotton mCotton;
    public CottonJsxxListAdapter cottonJsxxListAdapter;
    public CottonJsxxListAdapter cottonTwoAdapter;
    public CottonJsxxListAdapter cottonThreeAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cotton_cultivation;
    }

    public static void start(Context context, Cotton cotton) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("cotton", cotton);
        UiUtils.startActivity(context, CottonCultivationActivity.class, bundle);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        llList.setVisibility(View.GONE);
        llEnd0131.setVisibility(View.GONE);
        llEnd31.setVisibility(View.GONE);
        mCotton = (Cotton) getIntent().getSerializableExtra("cotton");
        cultivationAdapter = new CottonCultivationAdapter(this);
        cottonJsxxListAdapter = new CottonJsxxListAdapter(this);
        cottonTwoAdapter = new CottonJsxxListAdapter(this);
        cottonThreeAdapter = new CottonJsxxListAdapter(this);
        mCottonRecyclerview.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        mRecyclerViewList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerviewEnd0131.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        recyclerviewEnd31.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerViewList.setHasFixedSize(true);
        mCottonRecyclerview.setHasFixedSize(true);
        recyclerviewEnd0131.setHasFixedSize(true);
        recyclerviewEnd31.setHasFixedSize(true);
        mRecyclerViewList.setMotionEventSplittingEnabled(false);
        mCottonRecyclerview.setMotionEventSplittingEnabled(false);
        recyclerviewEnd0131.setMotionEventSplittingEnabled(false);
        recyclerviewEnd31.setMotionEventSplittingEnabled(false);
        mCottonRecyclerview.setAdapter(cultivationAdapter);
        mRecyclerViewList.setAdapter(cottonJsxxListAdapter);
        recyclerviewEnd31.setAdapter(cottonTwoAdapter);
        recyclerviewEnd0131.setAdapter(cottonThreeAdapter);

        mCottonRecyclerview.addItemDecoration(new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 45, 0));
        mRecyclerViewList.addItemDecoration(new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 45, 0));
        recyclerviewEnd31.addItemDecoration(new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 45, 0));
        recyclerviewEnd0131.addItemDecoration(new DividerDecoration(ContextCompat.getColor(this, R.color.common_divider_narrow), 1, 45, 0));

        if (mCotton.data != null) {
            cultivationAdapter.addAll(mCotton.data);
        }
        if (mCotton.jsxxList != null) {
            cottonJsxxListAdapter.addAll(mCotton.jsxxList);
            tvFirst.setText("交售信息列表  (共" + mCotton.jsxxList.size() + "条)");
        }
        if (mCotton.jsxxListEnd31 != null) {
            cottonTwoAdapter.addAll(mCotton.jsxxListEnd31);
            tvEnd31Sell.setText(Statistics(mCotton.jsxxListEnd31));
            tvTwo.setText("截止到" + mData + "交售信息列表  (共" + mCotton.jsxxListEnd31.size() + "条)");
        }
        if (mCotton.jsxxListEnd0131 != null) {
            cottonThreeAdapter.addAll(mCotton.jsxxListEnd0131);
            tvEnd0131Sell.setText(Statistics(mCotton.jsxxListEnd0131));
            tvThree.setText("截止到" + mData + "交售信息列表  (共" + mCotton.jsxxListEnd0131.size() + "条)");
        }
        if (!TextUtils.isEmpty(mCotton.jsInfo)) {
            String[] str = mCotton.jsInfo.split(",");
            tvListSell.setText("交售信息：共" + str[0] + "条，毛重(公斤)" + str[1] + ",皮重(公斤)" + str[2] + ",净重(公斤)" + str[3] + "。");
        }


    }

    //    public static String DBCToSBC(String input) {
//        char[] c = input.toCharArray();
//        for (int i = 0; i < c.length; i++) {
//            if (c[i] == 32) {
//                c[i] = (char) 12288;
//                continue;
//            }
//            if (c[i] < 127)
//                c[i] = (char) (c[i] + 65248);
//        }
//        return new String(c);
//    }
    ArrayList<Date> dateList = new ArrayList<Date>();

    public String Statistics(List<CottonJsxxList> list) {
        int grossWeigh = 0;
        int tareWeigh = 0;
        int netWeigh = 0;
        Date tempDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA);
        for (CottonJsxxList cottonJsxxList : list) {
            if ("2".equals(cottonJsxxList.seedState)) {
                grossWeigh += cottonJsxxList.grossWeigh;
                tareWeigh += cottonJsxxList.tareWeigh;
                netWeigh += cottonJsxxList.netWeigh;
            }
            try {
                dateList.add(format.parse(cottonJsxxList.upTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat(" yyyy.MM.dd ", Locale.CANADA);
        tempDate = dateList.get(0);
        for (int i = 0; i < dateList.size(); i++) {
            if (dateList.get(i).after(tempDate)) {
                tempDate = dateList.get(i);
            }
        }
        Calendar cal = Calendar.getInstance();
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.setTime(tempDate);
        // cal.set(Calendar.DATE, days);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        Date newD = cal.getTime();

        mData = sdf.format(newD);
        return "截止到" + sdf.format(newD) + "交售信息：共" + list.size() + "条，毛重(公斤)" + grossWeigh + ",皮重(公斤)" + tareWeigh + ",净重(公斤)" + netWeigh + "。";
    }


    @Override
    public void complete() {

    }

    @OnClick({R.id.fl_first_title, R.id.fl_two_title, R.id.fl_three_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_first_title:
                ivFirst.setSelected(!ivFirst.isSelected());
                if (llList.getVisibility() == View.VISIBLE) {
                    llList.setVisibility(View.GONE);
                } else {
                    llList.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.fl_two_title:
                ivTwo.setSelected(!ivTwo.isSelected());
                if (llEnd31.getVisibility() == View.VISIBLE) {
                    llEnd31.setVisibility(View.GONE);
                } else {
                    llEnd31.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.fl_three_title:
                ivThree.setSelected(!ivThree.isSelected());
                if (llEnd0131.getVisibility() == View.VISIBLE) {
                    llEnd0131.setVisibility(View.GONE);
                } else {
                    llEnd0131.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().post(new CottonRefreshCode());
        super.onDestroy();
    }
}
