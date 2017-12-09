package com.laoodao.smartagri.ui.farmland.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ejz.imageSelector.widget.PreviewViewPager;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.ui.farmland.contract.ImagePreviewContract;
import com.laoodao.smartagri.ui.farmland.presenter.ImagePreviewPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagePreviewActivity extends BaseActivity<ImagePreviewPresenter> implements ImagePreviewContract.ImagePreviewView {


    @BindView(R.id.previewViewPager)
    PreviewViewPager mPreviewViewPager;

    private List<String> mImageList = new ArrayList<>();

    private ImageFragmentAdapter mAdapter;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    public static void start(Context context, List<String> imageList, int selectPosition) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("IMAGE_LIST", (ArrayList<String>) imageList);
        bundle.putInt("SELECT_POSITION", selectPosition);
        UiUtils.startActivity(context, ImagePreviewActivity.class, bundle);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_preview;
    }

    @Override
    protected void configViews() {

        List<String> imageList = getIntent().getStringArrayListExtra("IMAGE_LIST");
        mImageList.addAll(imageList);
        int position = getIntent().getIntExtra("SELECT_POSITION", 0);
        mAdapter = new ImageFragmentAdapter();
        mPreviewViewPager.setAdapter(mAdapter);
        mPreviewViewPager.setCurrentItem(position);
    }


    @Override
    public void complete() {

    }


    class ImageFragmentAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View contentView = LayoutInflater.from(ImagePreviewActivity.this).inflate(com.ejz.imageSelector.R.layout.fragment_image_preview, container, false);
            final ImageView imageView = (ImageView) contentView.findViewById(com.ejz.imageSelector.R.id.preview_image);
            final PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);


            Glide.with(container.getContext())
                    .load(mImageList.get(position))
                    .into(new SimpleTarget<GlideDrawable>(480, 800) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageView.setImageDrawable(resource);
                            mAttacher.update();
                        }
                    });
            container.addView(contentView);
            return contentView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


}