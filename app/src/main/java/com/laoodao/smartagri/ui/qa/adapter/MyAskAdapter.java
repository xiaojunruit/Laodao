package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Ask;
import com.laoodao.smartagri.ui.qa.activity.QuestionDetailActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/18.
 */

public class MyAskAdapter extends BaseAdapter<Ask> {

    private SimpleDateFormat sf, sdf, sdfs;
    private String now;
    private Drawable drawable;

    public MyAskAdapter(Context context) {
        super(context);
        sf = new SimpleDateFormat("MM-dd hh:mm", Locale.CHINA);
        sdf = new SimpleDateFormat("HH:mm", Locale.CHINA);
        sdfs = new SimpleDateFormat("MM-dd HH:mm", Locale.CANADA);
        now = sf.format(new Date());
        drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_preview);
        drawable.setBounds(0, 0, UiUtils.dip2px(16), UiUtils.dip2px(16));
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAskItemHolder(parent);
    }

    class MyAskItemHolder extends BaseViewHolder<Ask> {
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.iv_image)
        RoundedImageView mIvImage;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_answer)
        TextView mTvAnswer;
        @BindView(R.id.cb_follow)
        TextView mCbFollow;
        @BindView(R.id.tv_browse)
        TextView mTvBrowse;

        public MyAskItemHolder(ViewGroup parent) {
            super(parent, R.layout.item_my_ask);

        }

        @Override
        public void setData(int position, Ask data) {
            Date d1 = null;
            int day = 0;
            try {
                Date date = sf.parse(data.time);
                Date date1 = sf.parse(now);
                long cha = date.getTime() - date1.getTime();
                day = Long.valueOf(cha / 86400000).intValue();//86400000=1000*60*60*24
                d1 = sdfs.parse(data.time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (day == 0) {
                mTvTime.setText(UiUtils.getString(R.string.today, sdf.format(d1)));
            } else if (day == -1) {
                mTvTime.setText(UiUtils.getString(R.string.yesterday, sdf.format(d1)));
            } else {
                mTvTime.setText(data.time);
            }
            mTvBrowse.setCompoundDrawables(drawable, null, null, null);
            mTvAnswer.setText(UiUtils.getString(R.string.comment_total, data.answerCount));
            mTvContent.setText(data.content);
            mTvBrowse.setText(UiUtils.getString(R.string.browse, data.browse));
            // mTvAddress.setText(data.address);

            if (TextUtils.isEmpty(data.thumbUrl)) {
                mIvImage.setVisibility(View.GONE);
            } else {
                mIvImage.setVisibility(View.VISIBLE);
                UiUtils.loadImage(mIvImage, data.thumbUrl);
            }
            itemView.setOnClickListener(v -> {
                QuestionDetailActivity.start(getContext(), getItem(position).id);
            });
        }
    }

}


