package com.laoodao.smartagri.ui.discovery.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Discover;
import com.laoodao.smartagri.bean.Discovercat;
import com.laoodao.smartagri.bean.Menu;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.ui.discovery.activity.CottonQueryActivity;
import com.laoodao.smartagri.ui.discovery.activity.MenuMoreActivity;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.NetworkUtils;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.tbruyelle.rxpermissions.RxPermissions;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/21.
 */

public class DiscoveryMenuAdapter extends BaseAdapter<Discovercat> {


    public DiscoveryMenuAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new DiscoveryMenuHodler(parent, R.layout.item_discovery_menu);
    }

    class DiscoveryMenuHodler extends BaseViewHolder<Discovercat> {
        @BindView(R.id.iv_img)
        ImageView mIvImg;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        public DiscoveryMenuHodler(ViewGroup parent, int viewType) {
            super(parent, viewType);
        }

        @Override
        public void setData(Discovercat data) {
            super.setData(data);
            Glide.with(getContext()).load(data.icon).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mIvImg);
            mTvTitle.setText(data.name);
            itemView.setOnClickListener(v -> {
                if (data.url.contains(Constant.PRICE_QUOTATION)) {
                    requestPermission(data.url.trim());
                    return;
                }
                if (data.url.contains(Constant.SCRATCH_CARD) && !NetworkUtils.isAvailable(getContext())) {
                    UiUtils.makeText("好像没有网络哦！");
                    return;
                }

                //判断是棉花查询并且不是棉花账单url
                if (data.url.startsWith(Route.COTTON) && !data.url.contains(Route.COTTON_BILL)) {
                    Route.go(getContext(), data.url.trim());
                    return;
                }
                startRoute(data.url.trim());
            });
        }

        private void startRoute(String url) {
            if (Global.getInstance().isLoggedIn()) {
                Route.go(getContext(), url);
            } else {
                LoginActivity.start(getContext(), true);
            }
        }

        private void requestPermission(String url) {
            PermissionUtil.externalStorage(new PermissionUtil.RequestPermissionListener() {
                @Override
                public void success() {
                    startRoute(url);
                }

                @Override
                public void failure() {
                    Toast.makeText(LDApplication.getInstance(), "请求权限失败,请前往设置开启权限！", Toast.LENGTH_SHORT).show();
                }
            }, new RxPermissions((Activity) getContext()));
        }
    }
}
