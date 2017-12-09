package com.laoodao.smartagri.ui.farmland.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Farmland;
import com.laoodao.smartagri.ui.farmland.activity.FarmlandDetailActivity;
import com.laoodao.smartagri.ui.farmland.activity.ImagePreviewActivity;

import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.text.NumberFormat;
import java.util.List;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/24.
 */

public class MyFarmlandAdapter extends BaseAdapter<Farmland> {
    public MyFarmlandAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyFarmlandHolder(parent, getItemCount());
    }


    static class MyFarmlandHolder extends BaseViewHolder<Farmland> {


        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_farland_name)
        TextView mTvFarlandName;
        @BindView(R.id.tv_area)
        TextView mTvArea;
        @BindView(R.id.image_content)
        LinearLayout mImageContent;
        @BindView(R.id.tv_location)
        TextView mTvLocation;
        @BindView(R.id.ll_content)
        LinearLayout mLlContent;
        @BindView(R.id.view_left)
        View mViewLeft;
        @BindView(R.id.view_right)
        View mViewRight;
        private int itemCount;

        public MyFarmlandHolder(ViewGroup itemView, int itemCount) {
            super(itemView, R.layout.item_my_farmland);
            this.itemCount = itemCount;
        }

        @Override
        public void setData(Farmland farmland) {
            mTvTime.setText(farmland.addTime);
            mTvFarlandName.setText(UiUtils.getString(R.string.farmland_name, farmland.cropName));

            mTvTime.setText(farmland.addTime);
            NumberFormat nf = NumberFormat.getInstance();
            mTvArea.setText(UiUtils.getString(R.string.farmland_area, nf.format(Double.parseDouble(farmland.acreage))));

            itemView.setOnClickListener(v -> {
                FarmlandDetailActivity.start(getContext(), farmland.id);
            });
            mTvLocation.setText(UiUtils.getString(R.string.farmland_location, farmland.local));
            addFarmlandImage(farmland.imageList);
            if (getCurrentPosition() == (itemCount - 1)) {
                mViewLeft.setVisibility(View.GONE);
                mViewRight.setVisibility(View.GONE);
            } else {
                mViewLeft.setVisibility(View.VISIBLE);
                mViewRight.setVisibility(View.VISIBLE);
            }
        }

        private void addFarmlandImage(List<String> imageList) {
            if (imageList != null && imageList.size() > 0) {
                mImageContent.setVisibility(View.VISIBLE);
                mImageContent.removeAllViews();
                for (int i = 0; i < imageList.size(); i++) {
                    String url = imageList.get(i);
                    ImageView imageView = new ImageView(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.weight = 1;
                    if (i < imageList.size() - 1)
                        params.rightMargin = UiUtils.dip2px(5);
                    imageView.setLayoutParams(params);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(getContext().getApplicationContext())
                            .load(url)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .dontAnimate()
                            .centerCrop()
                            .into(imageView);
                    int finalI = i;
                    imageView.setOnClickListener(v -> {
                        ImagePreviewActivity.start(getContext(), imageList, finalI);
                    });
                    mImageContent.addView(imageView);
                }
            } else {
                mImageContent.setVisibility(View.GONE);
            }
        }
    }

}
