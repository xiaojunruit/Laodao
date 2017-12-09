package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.flyco.tablayout.widget.MsgView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.UserMenu;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.common.Route;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/13.
 * <p>
 * 个人中心，菜单
 */

public class UserMenuAdapter extends BaseAdapter<UserMenu> {


    public static final int TYPE_DIVIDER = 1;
    public static final int TYPE_ITEM = 2;


    public UserMenuAdapter(Context context) {
        super(context);

    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_DIVIDER:
                return new DividerHolder(parent);
            default:
                return new UserMenuHolder(parent, R.layout.item_user_menu);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if ("divider".equals(getItem(position).url)) {
            return TYPE_DIVIDER;
        }
        return TYPE_ITEM;
    }


    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }



    class UserMenuHolder extends BaseViewHolder<UserMenu> {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.img_left)
        ImageView mImgLeft;
        @BindView(R.id.mv_chat_un_num)
        MsgView mMvChatUnNum;

        public UserMenuHolder(ViewGroup parent, @LayoutRes int res) {
            super(parent, res);
        }

        @Override
        public void setData(UserMenu data) {
            mTvTitle.setText(data.title);
            Glide.with(getContext().getApplicationContext())
                    .load(data.icon)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .into(mImgLeft);
            if (data.url.contains(Route.CHAT_LIST)&& Global.getInstance().isLoggedIn()) {
                mMvChatUnNum.setVisibility(View.VISIBLE);
                updateUnreadLabel();
            } else {
                mMvChatUnNum.setVisibility(View.GONE);
            }
        }

        /**
         * update unread message count
         */
        public void updateUnreadLabel() {
            int count = getUnreadMsgCountTotal();
            if (count > 0) {
                mMvChatUnNum.setText(String.valueOf(count));
            } else {
                mMvChatUnNum.setVisibility(View.GONE);
            }
        }

    }
}
