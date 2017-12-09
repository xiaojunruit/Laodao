package com.laoodao.smartagri.ui.discovery.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ejz.multistateview.MultiStateView;
import com.flyco.roundview.RoundTextView;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.NewDrug;
import com.laoodao.smartagri.bean.NewDrugTab;
import com.laoodao.smartagri.bean.Unipue;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.ReleaseFormulaEvent;
import com.laoodao.smartagri.ui.discovery.adapter.NewDrugAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.UnipueAdapter;
import com.laoodao.smartagri.ui.discovery.contract.CropDetailContract;
import com.laoodao.smartagri.ui.discovery.presenter.CropDetailPresenter;
import com.laoodao.smartagri.ui.qa.adapter.ImagePreviewActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.ObservableScrollView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/5/17.
 */

public class CropDetailActivity extends BaseActivity<CropDetailPresenter> implements CropDetailContract.CropDetailView, ObservableScrollView.ScrollListener {
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.tab)
    SegmentTabLayout mTab;
    @BindView(R.id.tv_mark)
    TextView mTvMark;
    @BindView(R.id.tv_tab_new)
    TextView mTvTabNew;
    @BindView(R.id.tv_tab_unique)
    TextView mTvTabUnique;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.tv_unique)
    RoundTextView mTvUnique;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    @BindView(R.id.tv_tab_title)
    TextView mTvTabTitle;
    @BindView(R.id.tv_mark_title)
    TextView mTvMarkTitle;
    @BindView(R.id.sticky)
    FrameLayout mSticky;
    @BindView(R.id.scrollView)
    ObservableScrollView mScrollView;
    @BindView(R.id.parent)
    LinearLayout mParent;
    @BindView(R.id.tv_tag_title)
    TextView mTvTagTitle;
    @BindView(R.id.iv_title_icon)
    ImageView mIvTitleIcon;
    @BindView(R.id.cb_collection)
    CheckBox mCbCollection;
    @BindView(R.id.collection)
    LinearLayout mCollection;
    @BindView(R.id.view_mark_title)
    View mViewMarkTitle;
    @BindView(R.id.iv_banner_bitmap)
    ImageView mIvBannerBitmap;
    private NewDrugTab newDrugTab;
    private NewDrugAdapter mNewDrugAdapter;
    private UnipueAdapter mUnipueAdapter;
    private int id;

    public static void start(Context context, int id) {
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        UiUtils.startActivity(context, CropDetailActivity.class, bundle);
    }

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_crop_detail;
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
        id = getIntent().getIntExtra("id", 0);
        initAdapter();
        initRecyclerView();
        mTvTabNew.setSelected(true);
        mPresenter.requestNewDrugTab(id);
        mPresenter.requestNewDrugList(id);
        mPresenter.requestUnipueList(id);
        mScrollView.registerListener(this);
        mSticky.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (mScrollView.getScrollY() != 0) {
                return;
            }
            onScroll(0, 0);
        });
    }

    private void initAdapter() {
        mNewDrugAdapter = new NewDrugAdapter(this);
        mUnipueAdapter = new UnipueAdapter(this);
//        mNewDrugAdapter.setCollectionListener(id -> {
//            mPresenter.requestDrugCollect(id);
//        });
    }

    private void initRecyclerView() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mNewDrugAdapter);
    }

    private void initBanner() {
        mBanner.setVisibility(newDrugTab.getUrls().size() < 1 ? View.GONE : View.VISIBLE);
        mIvBannerBitmap.setVisibility(newDrugTab.getUrls().size() < 1 ? View.VISIBLE : View.GONE);
        mBanner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                .isAutoPlay(false)
                .setImageLoader(new GlideImageLoader())
                .setBannerTitles(newDrugTab.getTitles())
                .setImages(newDrugTab.getUrls())
                .start();
        mBanner.setOnBannerListener(position -> {
            ImagePreviewActivity.start(this, newDrugTab.getUrls(), position);
        });
    }

    private void hideStickyView() {
        gone(mTvMarkTitle, mSticky, mViewMarkTitle);
    }

    private void showStickyView() {
        visible(mTvMarkTitle, mSticky, mViewMarkTitle);
    }

    private void initTab() {
        if (newDrugTab.getTabTitles() == null || newDrugTab.getTabContent() == null) {
            mTab.setVisibility(View.GONE);
            mTvTabTitle.setVisibility(View.GONE);
            mTvMark.setVisibility(View.GONE);
            return;
        }
        mTab.setVisibility(newDrugTab.getTabTitles().length > 1 ? View.VISIBLE : View.GONE);
        mTvTabTitle.setVisibility(newDrugTab.getTabTitles().length > 1 ? View.GONE : View.VISIBLE);
        mTab.setTabData(newDrugTab.getTabTitles());
        mTvTabTitle.setText(newDrugTab.getTabTitles()[0]);
        mTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                selectTabLayout(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void selectTabLayout(int position) {
        if (newDrugTab == null) {
            UiUtils.makeText("数据请求失败");
            return;
        }
        mTvMark.setText(newDrugTab.getTabContent().get(position));
    }


    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void releaseCrop(ReleaseFormulaEvent event) {
        mPresenter.requestUnipueList(id);
    }


    @Override
    public void newDrugTab(NewDrugTab newDrugTab) {
        if (newDrugTab == null) {
            return;
        }
        this.newDrugTab = newDrugTab;
        mTvTabNew.setText(newDrugTab.methodarr.get(0));
        mTvTabUnique.setText(newDrugTab.methodarr.get(1));
        if (newDrugTab.items.size() != 0) {
            mTvMark.setText(newDrugTab.getTabContent().get(0));
        }
        initTab();
        setTitle(newDrugTab.title);
        initBanner();
    }

    @Override
    public void unipueSuccess(List<Unipue> unipues) {
        mUnipueAdapter.addAll(unipues, true);
    }

    @Override
    public void newDrugListSuccess(List<NewDrug> newDrugs) {
        if (newDrugs.size() > 0) {
            mSticky.setVisibility(TextUtils.isEmpty(newDrugs.get(0).topTitle) ? View.GONE : View.VISIBLE);
            mTvMarkTitle.setVisibility(TextUtils.isEmpty(newDrugs.get(0).topTitle) ? View.GONE : View.VISIBLE);
            mViewMarkTitle.setVisibility(TextUtils.isEmpty(newDrugs.get(0).topTitle) ? View.GONE : View.VISIBLE);
            mTvTagTitle.setText(newDrugs.get(0).topTitle);
            Glide.with(this).load(newDrugs.get(0).icon).into(mIvTitleIcon);
        }
        mNewDrugAdapter.addAll(newDrugs, true);
    }

    @Override
    public void drugCollectSuccess() {

    }

    @OnClick({R.id.tv_tab_new, R.id.tv_tab_unique, R.id.tv_unique})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_unique:
                ReleaseFormulaActivity.start(this, id);
                break;
            case R.id.tv_tab_new:
                if (mTvTabNew.isSelected()) {
                    return;
                }
                if (mNewDrugAdapter.getAllData().size() > 0) {
                    if (!TextUtils.isEmpty(((NewDrug) mNewDrugAdapter.getItem(0)).topTitle)) {
                        showStickyView();
                    }
                }
                mTvTabNew.setSelected(true);
                mTvTabUnique.setSelected(false);
                mRecyclerview.setAdapter(mNewDrugAdapter);
                mNewDrugAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_tab_unique:
                if (mTvTabUnique.isSelected()) {
                    return;
                }
                hideStickyView();
                mTvTabUnique.setSelected(true);
                mTvTabNew.setSelected(false);
                mRecyclerview.setAdapter(mUnipueAdapter);
                mUnipueAdapter.notifyDataSetChanged();
                break;
        }
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

    @Override
    public void onScroll(int t, int l) {
        mSticky.setTranslationY(Math.max(t, mTvMarkTitle.getTop()));

    }

    class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path)
                    .error(R.mipmap.bg_big_seize_seat)
                    .placeholder(R.mipmap.bg_big_seize_seat)
                    .into(imageView);
        }
    }
}
