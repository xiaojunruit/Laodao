package com.laoodao.smartagri.ui.discovery.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.PriceQuotation;
import com.laoodao.smartagri.bean.ShareInfo;
import com.laoodao.smartagri.event.PriceQuotationFollowEvent;
import com.laoodao.smartagri.ui.discovery.activity.PriceDetailsActivity;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.qa.dialog.MenuPopup;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/7/28.
 */

public class PriceQuotationAdapter extends BaseAdapter<PriceQuotation> {

//    private ShareDialog mShareDialog;

    public PriceQuotationAdapter(Context context) {
        super(context);
//        mShareDialog = new ShareDialog(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PriceQuotationHolder(parent);
    }

    class PriceQuotationHolder extends BaseViewHolder<PriceQuotation> {

        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.iv_more)
        ImageView mIvMore;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        private MenuPopup mMenuPopup;
        private int id;
//        private int isWonder;

        public PriceQuotationHolder(ViewGroup parent) {
            super(parent, R.layout.item_price_quotation);
        }

        @Override
        public void setData(PriceQuotation data) {
            super.setData(data);
            mTvName.setText(data.name);
            mTvTime.setText(data.addTime);
            mTvPrice.setText(Html.fromHtml(UiUtils.getString(R.string.today_offer, data.price)));
            if (TextUtils.isEmpty(data.attribute)) {
                mTvContent.setVisibility(View.GONE);
            } else {
                mTvContent.setVisibility(View.VISIBLE);
            }
            mTvContent.setText(data.attribute);
            mTvAddress.setText(data.areaName);
            id = data.id;
            itemView.setOnClickListener(v -> {
                PriceDetailsActivity.start(getContext(), data.id);
            });
//            isWonder = data.isWonder;
//            mShareDialog.setShareInfo(data.shareInfo);
        }

        @OnClick(R.id.ll_see_more)
        public void onClick() {
            if (id == 0) {
                return;
            }
            PriceDetailsActivity.start(getContext(), id);
        }

//        @OnClick({R.id.iv_more})
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.iv_more:
//                    if (mMenuPopup == null) {
//                        mMenuPopup = new MenuPopup(getContext(), this);
//                    }
//                    mMenuPopup.setCenterVisible(false);
//                    mMenuPopup.anchorView(mIvMore);
//                    mMenuPopup.setLeftText(isFollow ? UiUtils.getString(R.string.unfollow) : UiUtils.getString(R.string.follow_price));
//                    mMenuPopup.show();
//                    break;
//                case R.id.tv_left:
//                    PriceQuotation price = getItem(getCurrentPosition());
//                    EventBus.getDefault().post(new PriceQuotationFollowEvent(price.id));
//                    price.isFollow = !isFollow;
//                    break;
//                case R.id.tv_right:
//                    mShareDialog.show();
//                    break;
//            }
//        }
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mShareDialog.onActivityResult(requestCode, resultCode, data);
//    }
}
