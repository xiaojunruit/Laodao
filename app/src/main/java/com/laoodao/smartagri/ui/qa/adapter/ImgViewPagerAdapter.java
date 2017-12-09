package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.bean.Img;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2016/12/9.
 */

public class ImgViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<Img> mImages = new ArrayList<>();

    public ImgViewPagerAdapter(Context context, List<Img> imgsId) {
        mContext = context;
        mImages = imgsId;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    private OnCLickListener onCLickListener;
    public void setOnClickListener(OnCLickListener onClickListener) {
        this.onCLickListener = onClickListener;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoView view = new PhotoView(mContext);
        view.setScaleType(ImageView.ScaleType.FIT_CENTER);
        String url = mImages.get(position).url;
        Glide.with(mContext).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(view);
        container.addView(view);
        view.setOnClickListener(v -> {
            this.onCLickListener.setOnClickListener();
        });
        return view;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface OnCLickListener {
        void setOnClickListener();
    }
}
