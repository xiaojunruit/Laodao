package com.laoodao.smartagri.ui.farmland.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.FarmlandDetail;
import com.laoodao.smartagri.ui.farmland.activity.ImagePreviewActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.laoodao.smartagri.view.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/24.
 */

public class FarmlandDetailAdapter extends BaseAdapter<FarmlandDetail> {


    public FarmlandDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new FarmlandDetailItemHolder(parent, R.layout.item_farmland_detail, getItemCount());
    }

    class FarmlandDetailItemHolder extends BaseViewHolder<FarmlandDetail> {
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_farland_title)
        TextView mTvFarlandTitle;
        @BindView(R.id.content)
        LinearLayout mContent;
        @BindView(R.id.tv_location)
        TextView mTvLocation;
        @BindView(R.id.ll_content)
        LinearLayout mLlContent;
        @BindView(R.id.view_left)
        View mViewLeft;
        @BindView(R.id.view_right)
        View mViewRight;
        private int itemCount;

        public FarmlandDetailItemHolder(ViewGroup parent, @LayoutRes int viewType, int itemCount) {
            super(parent, viewType);
            this.itemCount = itemCount;
        }

        @Override
        public void setData(FarmlandDetail data) {
            mTvTime.setText(data.time);
            mTvFarlandTitle.setText(data.type_name);
            mTvLocation.setText(UiUtils.getString(R.string.spend, data.countMoney));
            addFarmlandImage(data.imgarr);
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
                mContent.setVisibility(View.VISIBLE);
                mContent.removeAllViews();
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
                    mContent.addView(imageView);
                }
            } else {
                mContent.setVisibility(View.GONE);
            }
        }
    }

}
