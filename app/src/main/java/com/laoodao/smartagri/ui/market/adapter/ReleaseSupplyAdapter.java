package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.dialog.widget.NormalDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.SupplyMy;
import com.laoodao.smartagri.bean.Supplylists;
import com.laoodao.smartagri.event.DelSaleEvent;
import com.laoodao.smartagri.ui.market.activity.ReleaseSupplyingActivity;
import com.laoodao.smartagri.ui.market.activity.SupplyDetailsActivity;
import com.laoodao.smartagri.ui.market.dialog.SalesMorePopup;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.qa.adapter.ImagePreviewActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/18.
 */

public class ReleaseSupplyAdapter extends BaseAdapter<SupplyMy> {


    public ReleaseSupplyAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(parent, R.layout.item_release_supply);
    }


    class MyHolder extends BaseViewHolder<SupplyMy> implements View.OnClickListener {
        @BindView(R.id.supplyDetails)
        LinearLayout mSupplyDetails;
        @BindView(R.id.iv_more)
        ImageView mIvMore;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.tv_publishtime)
        TextView mTvPublishtime;
        @BindView(R.id.iv_image)
        ImageView mIvImage;
        @BindView(R.id.tv_view)
        TextView mTvView;

        private SalesMorePopup salesMorePopup;
        private ShareDialog shareDialog;

        @Override
        public void setData(SupplyMy data) {
            super.setData(data);
            mTvTitle.setText(data.title);
            mTvCategory.setText(data.category);
            mTvPublishtime.setText(UiUtils.getString(R.string.add_time, data.addTime));
            mTvPublishtime.setVisibility(data.addTime.isEmpty() ? View.GONE : View.VISIBLE);
            mTvView.setText(UiUtils.getString(R.string.view, data.views));
            mTvView.setVisibility(data.views.isEmpty() ? View.GONE : View.VISIBLE);
            if (data.thumb.length != 0) {
                mIvImage.setVisibility(View.VISIBLE);
                Glide.with(getContext()).load(data.thumb[0])
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .dontAnimate().into(mIvImage);
            } else {
                mIvImage.setVisibility(View.GONE);
            }


        }

        @OnClick(R.id.iv_image)
        public void onClick() {
            SupplyMy item = getItem(getCurrentPosition());
            List<String> imgList = new ArrayList<>();
            for (int i = 0; i < item.thumb.length; i++) {
                imgList.add(item.thumb[i]);
            }
            ImagePreviewActivity.start(getContext(), imgList, 0);
        }

        public MyHolder(ViewGroup parent, @LayoutRes int viewType) {
            super(parent, viewType);
        }

        @OnClick({R.id.supplyDetails, R.id.iv_more})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.supplyDetails:
                    SupplyDetailsActivity.start(getContext(), "我的供销详情", getItem(getCurrentPosition()).id);
                    break;
                case R.id.iv_more:
                    if (salesMorePopup == null)
                        salesMorePopup = new SalesMorePopup(getContext(), this);
                    salesMorePopup.anchorView(mIvMore);
                    salesMorePopup.show();
                    break;
                case R.id.tv_left:
                    ReleaseSupplyingActivity.start(getContext(), "我的发布供销", getItem(getCurrentPosition()).id);
                    break;
                case R.id.tv_center:
                    NormalDialog dialog = new NormalDialog(getContext());
                    dialog.content("确认删除该条信息?")
                            .contentGravity(Gravity.CENTER)
                            .contentTextColor(R.color.common_h1)
                            .isTitleShow(false)
                            .cornerRadius(5)
                            .contentTextSize(16f)
                            .btnTextSize(14f, 14f)
                            .btnTextColor(R.color.common_h3, R.color.common_h3)
                            .show();
                    dialog.setOnBtnClickL(() -> {
                        dialog.dismiss();
                    }, () -> {
                        DelSaleEvent buyEvent = new DelSaleEvent();
                        buyEvent.buyId = getItem(getCurrentPosition()).id;
                        buyEvent.itemId = getCurrentPosition();
                        EventBus.getDefault().post(buyEvent);
                        dialog.dismiss();
                    });
                    break;
                case R.id.tv_right:
                    if (shareDialog == null) {
                        shareDialog = new ShareDialog(getContext());
                    }
                    shareDialog.setShareInfo(getItem(getCurrentPosition()).shareInfo);
                    shareDialog.show();
                    break;
            }
        }

    }


}


