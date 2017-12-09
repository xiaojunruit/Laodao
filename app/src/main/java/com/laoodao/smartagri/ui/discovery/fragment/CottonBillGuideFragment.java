package com.laoodao.smartagri.ui.discovery.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.LazyFragment;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.contract.CottonBillGuideContract;
import com.laoodao.smartagri.ui.discovery.presenter.CottonBillGuidePresenter;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;

/**
 * Created by WORK on 2017/7/20.
 */

public class CottonBillGuideFragment extends LazyFragment<CottonBillGuidePresenter> implements CottonBillGuideContract.CottonBillGuideView {
    @BindView(R.id.iv_tu)
    ImageView mIvTu;
//    @BindView(R.id.mark)
//    LinearLayout mMark;

    @Override
    public void complete() {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_cotton_bill_guide;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

//    String path1 = "";

    @Override
    public void configViews() {

    }

    @Override
    protected void lazyFetchData() {
//        mPresenter.cottonIndex();
    }

//    @Override
//    public void setImage(String img) {
//        Glide.with(getContext())
//                .load(img)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(new GlideDrawableImageViewTarget(mIvTu) {
//                    @Override
//                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                        super.onResourceReady(resource, animation);
//                        mMark.setVisibility(View.VISIBLE);
//                    }
//                });
//
//    }
}
