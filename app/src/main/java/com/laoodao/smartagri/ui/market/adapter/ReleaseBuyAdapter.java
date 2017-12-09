package com.laoodao.smartagri.ui.market.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.NormalDialog;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.SupplyMy;
import com.laoodao.smartagri.event.DelBuyEvent;
import com.laoodao.smartagri.event.ShareIdEvent;
import com.laoodao.smartagri.ui.market.activity.BuyDetailsActivity;
import com.laoodao.smartagri.ui.market.activity.ReleaseBuyingActivity;
import com.laoodao.smartagri.ui.market.dialog.SalesMorePopup;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.market.presenter.ReleaseBuyPresenter;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/18.
 */

public class ReleaseBuyAdapter extends BaseAdapter<SupplyMy> {


    private ReleaseBuyPresenter mPresenter;

    public ReleaseBuyAdapter(Context context, ReleaseBuyPresenter presenter) {
        super(context);
        this.mPresenter = presenter;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReleaseBuyHolder(parent, R.layout.item_release_buy);
    }


    class ReleaseBuyHolder extends BaseViewHolder<SupplyMy> implements View.OnClickListener {
        @BindView(R.id.ll_buyDetails)
        LinearLayout mLlBuyDetails;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.tv_publishtime)
        TextView mTvPublishtime;
        @BindView(R.id.tv_view)
        TextView mTvView;
        private SalesMorePopup salesMorePopup;
        @BindView(R.id.iv_more)
        ImageView mIvMore;
        private ShareDialog shareDialog;

        public ReleaseBuyHolder(ViewGroup parent, @LayoutRes int viewType) {
            super(parent, viewType);
        }


        @Override
        public void setData(SupplyMy data) {
            super.setData(data);
            mTvTitle.setText(data.title);
            mTvCategory.setText(data.category);
            mTvPublishtime.setText(data.addTime);
            mTvPublishtime.setVisibility(data.addTime.isEmpty() ? View.GONE : View.VISIBLE);
            mTvView.setText(UiUtils.getString(R.string.view, data.views));
            mTvView.setVisibility(data.views.isEmpty() ? View.GONE : View.VISIBLE);
            itemView.setOnClickListener(v -> {
                BuyDetailsActivity.start(getContext(), "我的求购详情", data.id);
            });

        }

        @OnClick({R.id.ll_buyDetails, R.id.iv_more})
        public void onClick(View view) {
            SupplyMy data = getItem(getCurrentPosition());
            switch (view.getId()) {
//                case R.id.rtv_edit:
//                    ReleaseBuyingActivity.start(getContext(), "我的发布求购", getItem(getCurrentPosition()).id);
//                    break;
//                case R.id.rtv_delete:
//                    NormalDialog dialog = new NormalDialog(getContext());
//                    dialog.content("确认删除该条信息?")
//                            .contentGravity(Gravity.CENTER)
//                            .contentTextColor(R.color.common_h1)
//                            .isTitleShow(false)
//                            .cornerRadius(5)
//                            .contentTextSize(16f)
//                            .btnTextSize(14f, 14f)
//                            .btnTextColor(R.color.common_h3, R.color.common_h3)
//                            .show();
//                    dialog.setOnBtnClickL(() -> {
//                        dialog.dismiss();
//                    }, () -> {
//                        DelBuyEvent buyEvent = new DelBuyEvent();
//                        buyEvent.buyId = getItem(getCurrentPosition()).id;
//                        EventBus.getDefault().post(buyEvent);
//                        dialog.dismiss();
//                    });
//                    break;
//                case R.id.rtv_share:
//                    if (mShareDialog == null) mShareDialog = new ShareDialog(getContext());
//                    mShareDialog.setShareInfo(data.shareInfo);
//                    mShareDialog.show();
//                    break
                case R.id.ll_buyDetails:
                    BuyDetailsActivity.start(getContext(), "我的求购详情", getItem(getCurrentPosition()).id);
                    break;
                case R.id.iv_more:
                    if (salesMorePopup == null)
                        salesMorePopup = new SalesMorePopup(getContext(), this);
                    salesMorePopup.anchorView(mIvMore);
                    salesMorePopup.show();
                    break;
                case R.id.tv_left:
                    ReleaseBuyingActivity.start(getContext(), "我的发布求购", getItem(getCurrentPosition()).id);
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
                        DelBuyEvent buyEvent = new DelBuyEvent();
                        buyEvent.itemId = getCurrentPosition();
                        buyEvent.buyId = getItem(getCurrentPosition()).id;
                        EventBus.getDefault().post(buyEvent);
                        dialog.dismiss();
                    });
                    break;
                case R.id.tv_right:
                    if (shareDialog == null) {
                        shareDialog = new ShareDialog(getContext());
                    }
                    SupplyMy supplyMy = getItem(getCurrentPosition());
                    shareDialog.setShareInfo(supplyMy.shareInfo);
                    ShareIdEvent shareIdEvent = new ShareIdEvent();
                    shareIdEvent.id = supplyMy.id;
                    shareIdEvent.shareDialog = shareDialog;
                    EventBus.getDefault().post(shareIdEvent);
                    shareDialog.show();
                    break;


            }
        }
    }

}

