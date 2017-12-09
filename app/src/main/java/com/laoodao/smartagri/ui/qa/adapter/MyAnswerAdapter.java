package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.MyAnswer;
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

public class MyAnswerAdapter extends BaseAdapter<MyAnswer> {


    private SimpleDateFormat sf, sdf, sdfs;
    private String now;

    public MyAnswerAdapter(Context context) {
        super(context);
        sf = new SimpleDateFormat("MM-dd hh:mm", Locale.CANADA);
        sdf = new SimpleDateFormat("HH:mm", Locale.CANADA);
        sdfs = new SimpleDateFormat("MM-dd HH:mm", Locale.CANADA);
        now = sf.format(new Date());
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyAnswerHolder(parent);
    }

    class MyAnswerHolder extends BaseViewHolder<MyAnswer> {


        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_answer)
        TextView mTvAnswer;
        @BindView(R.id.img_content)
        RoundedImageView mImgContent;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;

        public MyAnswerHolder(ViewGroup parent) {
            super(parent, R.layout.item_my_answer);

        }

        @Override
        public void setData(int position, MyAnswer data) {
            Date d1 = null;
            int day = 0;
            try {
                Date date = sf.parse(data.time);
                Date date1 = sf.parse(now);
                long cha = date.getTime() - date1.getTime();
                day = Long.valueOf(cha / (86400000)).intValue();//86400000=1000*60*60*24
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

            mTvContent.setText(data.askContent);
            mTvTitle.setText(data.title);
            mTvAnswer.setText(data.content);
            UiUtils.loadImage(mImgContent, data.image);
            itemView.setOnClickListener(v -> {
                QuestionDetailActivity.start(getContext(), getItem(position).askId);
            });
        }
    }

}


