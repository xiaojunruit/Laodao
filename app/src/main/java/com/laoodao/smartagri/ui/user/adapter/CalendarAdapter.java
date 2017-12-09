package com.laoodao.smartagri.ui.user.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Calendar;
import com.laoodao.smartagri.bean.SignIn;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/4/25.
 */

public class CalendarAdapter extends BaseAdapter<Calendar> {
    public CalendarAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new CalenderHoder(parent);
    }

    public void setSignedData(SignIn.Calendar calendar) {

        List<Calendar> calendarList = getAllData();
        java.util.Calendar todayCalendar = java.util.Calendar.getInstance();
        int day = todayCalendar.get(java.util.Calendar.DATE);
        int month = todayCalendar.get(java.util.Calendar.MONTH);
        int year = todayCalendar.get(java.util.Calendar.YEAR);
        for (Calendar c : calendarList) {
            if (c.year == calendar.year && c.month == calendar.month) {
                for (Integer log : calendar.logs) {
                    if (log == c.day) {
                        if (day == c.day && (month + 1) == c.month && year == c.year) {
                            c.isSignToday = true;
                        } else {
                            c.isSelected = true;
                        }
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    static class CalenderHoder extends BaseViewHolder<Calendar> {

        @BindView(R.id.tv_sign)
        TextView mTvSign;
        @BindView(R.id.img_sign)
        ImageView mImgSign;

        public CalenderHoder(ViewGroup itemView) {
            super(itemView, R.layout.item_calender);
        }

        @Override
        public void setData(Calendar calendar) {
            mTvSign.setText(String.valueOf(calendar.day));
            mImgSign.setVisibility(calendar.isSelected ? View.VISIBLE : View.GONE);
            if (calendar.isSignToday) {
                mTvSign.setBackgroundResource(R.mipmap.ic_sign_totay);
            }
            if (calendar.isCurrent) {
                mTvSign.setTextColor(UiUtils.getColor(R.color.common_h1));
            } else {
                mTvSign.setTextColor(UiUtils.getColor(R.color.common_h3));
            }
        }
    }
}
