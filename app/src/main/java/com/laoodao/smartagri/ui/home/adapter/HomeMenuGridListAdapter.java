package com.laoodao.smartagri.ui.home.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Menu;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/29.
 */

public class HomeMenuGridListAdapter extends BaseAdapter<Menu> {


    public HomeMenuGridListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuHolder(parent);
    }


    class MenuHolder extends BaseViewHolder<Menu> {

        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_mark)
        TextView mTvMark;
        @BindView(R.id.ll_release_supply)
        LinearLayout mLlReleaseSupply;

        public MenuHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_home_menu_gridlist);
        }

        @Override
        public void setData(Menu data) {
            mTvTitle.setText(data.title);
            mTvMark.setText(data.desc);
            UiUtils.loadImage(mIvImg, data.icon);

            itemView.setOnClickListener(v -> {
                if ((data.url.contains(Route.QUESTIONS) || data.url.contains(Route.MARKET_RELEASE)) && !Global.getInstance().isLoggedIn()) {
                    LoginActivity.start(getContext(), true);
                    return;
                }
                Route.go(getContext(), data.url);
            });
        }
    }
}
