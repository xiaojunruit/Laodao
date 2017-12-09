package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.ejz.imageSelector.widget.PreviewViewPager;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.utils.UiUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImagePreviewActivity extends AppCompatActivity {


    PreviewViewPager mPreviewViewPager;

    private List<String> mImageList = new ArrayList<>();

    private ImageFragmentAdapter mAdapter;
    TextView mIndicator;

    public static void start(Context context, List<String> imageList, int selectPosition) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("IMAGE_LIST", (ArrayList<String>) imageList);
        bundle.putInt("SELECT_POSITION", selectPosition);
        UiUtils.startActivity(context, ImagePreviewActivity.class, bundle);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_image_preview);
        mPreviewViewPager = (PreviewViewPager) findViewById(R.id.previewViewPager);
        List<String> imageList = getIntent().getStringArrayListExtra("IMAGE_LIST");
        mImageList.addAll(imageList);
        int position = getIntent().getIntExtra("SELECT_POSITION", 0);
        mAdapter = new ImageFragmentAdapter();
        mPreviewViewPager.setAdapter(mAdapter);
        mPreviewViewPager.setCurrentItem(position);
        mIndicator = (TextView) findViewById(R.id.tv_indicator);

        mPreviewViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.setText((position + 1) + "/" + mAdapter.getCount());
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

            View contentView = LayoutInflater.from(ImagePreviewActivity.this).inflate(R.layout.item_image_preview, container, false);
            final ImageView imageView = (ImageView) contentView.findViewById(R.id.preview_image);
            final PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageView);


            Glide.with(container.getContext())
                    .load(mImageList.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(new SimpleTarget<GlideDrawable>(480, 800) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            imageView.setImageDrawable(resource);
                            mAttacher.update();
                        }

                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                        }
                    });
            mAttacher.setOnViewTapListener((view, x, y) -> {
                finish();
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