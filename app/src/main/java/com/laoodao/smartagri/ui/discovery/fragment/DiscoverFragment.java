package com.laoodao.smartagri.ui.discovery.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.ejz.xrecyclerview.XRecyclerView;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.Discovercat;
import com.laoodao.smartagri.bean.NearbyShop;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.SelectAreaChangeEvent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.ui.discovery.activity.EncyclopediaActivity;
import com.laoodao.smartagri.ui.discovery.activity.MyAttentionPriceActivity;
import com.laoodao.smartagri.ui.discovery.activity.NearbyShopDetailActivity;
import com.laoodao.smartagri.ui.discovery.activity.NewsActivity;
import com.laoodao.smartagri.ui.discovery.activity.TruthQueryActivity;
import com.laoodao.smartagri.ui.discovery.adapter.DiscoveryAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.DiscoveryMenuAdapter;
import com.laoodao.smartagri.ui.discovery.contract.DiscoverContract;
import com.laoodao.smartagri.ui.discovery.presenter.DiscoverPresenter;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WORK on 2017/4/15.
 */

public class DiscoverFragment extends BaseXRVFragment<DiscoverPresenter> implements DiscoverContract.FindView {

    private String mLatitude;
    private String mLongitude;
    private String url;
    DiscoveryHeader header;
    int mErrorCount = 0;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_discovery;
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
        mHeader = new DiscoveryHeader(getContext());
        header = ((DiscoveryHeader) mHeader);
        initAdapter(DiscoveryAdapter.class);
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), 1);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setRefreshBackroundRes(R.color.background);
        mAdapter.setOnItemClickListener(position -> {
            NearbyShopDetailActivity.start(getContext(), ((NearbyShop) mAdapter.getItem(position)).storeCode,((NearbyShop) mAdapter.getItem(position)).storeId);

        });
    }

    @Override
    protected void lazyFetchData() {
        checkLocation();
        //已经手动选择过定位
        SelectLocation selectLocation = Global.getInstance().getCurrentLocation();
        if (selectLocation != null) {
            mLatitude = selectLocation.latitude;
            mLongitude = selectLocation.longitude;
            onRefresh();
        } else {
            requestLocation();
            mPresenter.requestMeun(0);
        }

    }

    private void requestLocation() {
        RxLocation.get().locate(mActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LocationSubscriber() {
                    @Override
                    public void onLocatedSuccess(@NonNull BDLocation bdLocation) {
                        Global.getInstance().setCurrentLocation(bdLocation);
                        lazyFetchData();
                        header.mNoLocation.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLocatedFail(BDLocation bdLocation) {
                        onContent();
                        header.mNoLocation.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    /**
     * 切换城市刷新列表数据
     *
     * @return
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectAreaChange(SelectAreaChangeEvent event) {
        onRefresh();
    }


    @Override
    public void onResume() {
        super.onResume();
        checkLocation();
    }


    private void checkLocation() {
        if (!PermissionUtil.judgeLocation(new RxPermissions(getActivity()))) {
            header.mNoLocation.setVisibility(View.VISIBLE);
            header.mTvFollow.setText("去设置");
            header.mTvErrorMark.setText(Html.fromHtml("定位服务暂未开启<br/>\n" +
                    "请在系统设置中开启定位服务"));
            header.mTvFollow.setVisibility(View.VISIBLE);
        } else if (Global.getInstance().getCurrentLocation() == null) {
            header.mTvErrorMark.setText("定位失败");
            header.mTvFollow.setText("重试");
            header.mTvFollow.setVisibility(View.VISIBLE);
            header.mNoLocation.setVisibility(View.VISIBLE);
        } else if (header.mNoLocation.getVisibility() == View.VISIBLE) {
            header.mNoLocation.setVisibility(View.GONE);
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.requestMeun(0);
        if (!PermissionUtil.judgeLocation(new RxPermissions(getActivity()))) {
            complete();
            return;
        }
        header.mNoLocation.setVisibility(View.GONE);
        mPresenter.requestDiscover(page, mLatitude, mLongitude);

    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        if (!PermissionUtil.judgeLocation(new RxPermissions(getActivity())) || Global.getInstance().getCurrentLocation() == null) {
            noMore(false);
            return;
        }
        mPresenter.requestDiscover(page, mLatitude, mLongitude);

    }

    @Override
    public void discoverSuccess(List<NearbyShop> list, boolean isRefresh) {
        mAdapter.addAll(list, isRefresh);
        if (list.size() == 0) {
            header.mNoLocation.setVisibility(View.VISIBLE);
            header.mTvErrorMark.setText("暂无数据");
            header.mTvFollow.setVisibility(View.GONE);
        } else {
            header.mNoLocation.setVisibility(View.GONE);
        }
    }

    @Override
    public void menuSucess(List<Discovercat> data) {
        // header.manager.setSpanCount(data.size());
        header.menuAdapter.addAll(data, true);
    }


    class DiscoveryHeader extends BaseHeaderView {
        @BindView(R.id.btn_news)
        LinearLayout mBtnNews;
        @BindView(R.id.btn_encyclopedia)
        LinearLayout mBtnEncyclopedia;
        @BindView(R.id.btn_query)
        LinearLayout mBtnQuery;
        @BindView(R.id.tv_error_mark)
        TextView mTvErrorMark;
        @BindView(R.id.tv_follow)
        RoundTextView mTvFollow;
        @BindView(R.id.no_location)
        LinearLayout mNoLocation;
        @BindView(R.id.recyclerview)
        RecyclerView mRecyclerview;
        private DiscoveryMenuAdapter menuAdapter;
        GridLayoutManager manager;


        public DiscoveryHeader(Context context) {
            super(context);
            menuAdapter = new DiscoveryMenuAdapter(context);
            menuAdapter.setHasStableIds(true);
            mRecyclerview.setAdapter(menuAdapter);
            manager = new GridLayoutManager(getContext(), 4) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            mRecyclerview.setLayoutManager(manager);
        }

        @OnClick({R.id.btn_news, R.id.btn_encyclopedia, R.id.btn_query, R.id.tv_follow})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_follow:
                    if (!PermissionUtil.judgeLocation(new RxPermissions(getActivity()))) {
                        PermissionUtil.startActionAetailsSettings(getActivity());
                    } else {
                        requestLocation();
                    }
                    break;
                case R.id.btn_news:
                    UiUtils.startActivity(NewsActivity.class);
                    break;
                case R.id.btn_encyclopedia:
                    UiUtils.startActivity(EncyclopediaActivity.class);
                    break;
                case R.id.btn_query:
                    UiUtils.startActivity(TruthQueryActivity.class);
                    break;
            }
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.item_discovery_header;
        }
    }
}
