package com.laoodao.smartagri.ui.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class HomeMenuListAdapter extends BaseAdapter<Menu> {

    private Context mContext;

    public HomeMenuListAdapter(Context context) {
        super(context);
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new HomeMenuHolder(parent);
    }


    class HomeMenuHolder extends BaseViewHolder<Menu> {

        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_mark)
        TextView mTvMark;
        @BindView(R.id.ll_books)
        LinearLayout mLlBooks;

        public HomeMenuHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_home_menu_list);
        }

        @Override
        public void setData(Menu data) {
            mTvTitle.setText(data.title);
            mTvMark.setText(data.desc);
            UiUtils.loadImage(mIvImg, data.icon);
            itemView.setOnClickListener(v -> {
                if ((data.url.contains("discovery/cotton_bill")||(data.url.contains("farmland")||data.url.contains("sign")))&&!Global.getInstance().isLoggedIn()){
                  //  Toast.makeText(mContext,"你还没登录！"+Toas)
                    Log.d("1xm",Log.getStackTraceString(new Throwable()));

                    LoginActivity.start(getContext(), true);
                    return;
                }
                Route.go(getContext(), data.url);
            });
        }
    }
}
