package com.laoodao.smartagri.view.cityselector.adapter;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.db.Area;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.view.cityselector.util.PinyinUtils;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 欧源 on 2017/5/3.
 */

public class CityAdapter extends BaseAdapter<Area> {


    private final int TYPE_LOCATION = 10005;

    private final int TYPE_CONTENT = 10001;

    private final int TYPE_CURRENT = 10000;

    private OnItemCotnentClickListener mOnItemCotnentClickListener;


    private OnItemLocationClickListener mOnItemLocationClickListener;
    HashMap<String, Integer> mLetterIndexes = new HashMap<>();

    private String mLocation;


    public String mCurrentCity;


    public CityAdapter(Context context, List<Area> objects, String currentCity) {
        super(context, objects);
        int size = objects.size();
        this.mCurrentCity = currentCity;
        objects.add(0, new Area("当前", "0"));
        objects.add(1, new Area("定位", "1"));
//        objects.add(2, new Area("最近", "2"));
        for (int i = 0; i < size; i++) {
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(objects.get(i).pinyin);
            //上个首字母，如果不存在设为""
            String previousLetter = i >= 1 ? PinyinUtils.getFirstLetter(objects.get(i - 1).pinyin) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                mLetterIndexes.put(currentLetter, i);
            }
        }
    }

    public void updateLocation(String location) {
        mLocation = location;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     *
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter) {
        Integer integer = mLetterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOCATION) {
            return new LocationHolder(parent);
        } else if (viewType == TYPE_CURRENT) {
            return new CurrentHolder(parent);
        }
        return new CityHolder(parent);
    }

    class CityHolder extends BaseViewHolder<Area> {

        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_letter)
        TextView mTvLetter;

        public CityHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_city);
        }

        @Override
        public void setData(int position, Area data) {
            mTvTitle.setText(data.name);
            String currentLetter = PinyinUtils.getFirstLetter(getItem(position).pinyin);
            String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(getItem(position - 1).pinyin) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)) {
                mTvLetter.setText(currentLetter);
                mTvLetter.setVisibility(View.VISIBLE);
            } else {
                mTvLetter.setVisibility(View.GONE);
            }
            itemView.setOnClickListener(v -> {
                if (mOnItemCotnentClickListener != null) {
                    mOnItemCotnentClickListener.onClick(v, position, data);
                }
            });
        }
    }

    class LocationHolder extends BaseViewHolder<Area> {
        @BindView(R.id.layout_locate)
        LinearLayout mLayoutLocate;
        @BindView(R.id.tv_locate)
        RoundTextView mTvLocate;

        public LocationHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_location);
        }

        @Override
        public void setData(int position, Area data) {
            if (!PermissionUtil.judgeLocation(new RxPermissions((Activity) getContext()))) {
                mTvLocate.setText("定位失败");
            } else {
                if (TextUtils.isEmpty(mLocation)) {
                    mTvLocate.setText("定位中...");
                } else {
                    mTvLocate.setText(mLocation);
                }
            }
            mTvLocate.setOnClickListener(v -> {
                if (mOnItemLocationClickListener != null) {
                    mOnItemLocationClickListener.onClick(v, mLocation);
                }
            });
        }
    }

    class CurrentHolder extends BaseViewHolder<Area> {

        @BindView(R.id.tv_current)
        TextView mTvCurrent;

        public CurrentHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_current);
        }

        @Override
        public void setData(Area data) {
            if (TextUtils.isEmpty(mCurrentCity)) {
                mTvCurrent.setText("");
            } else {
                mTvCurrent.setText(mCurrentCity);
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 1) {
            return TYPE_LOCATION;
        } else if (position == 0) {
            return TYPE_CURRENT;
        }
        return TYPE_CONTENT;
    }

    public interface OnItemCotnentClickListener {
        void onClick(View view, int position, Area data);
    }

    public interface OnItemLocationClickListener {
        void onClick(View view, String location);
    }

    public void setOnItemCotnentClickListener(OnItemCotnentClickListener onItemCotnentClickListener) {
        this.mOnItemCotnentClickListener = onItemCotnentClickListener;
    }

    public void setOnItemLocationClickListener(OnItemLocationClickListener onItemLocationClickListener) {
        this.mOnItemLocationClickListener = onItemLocationClickListener;
    }
}
