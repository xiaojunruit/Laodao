package com.laoodao.smartagri.view.cityselector.adapter;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Pos;
import com.laoodao.smartagri.db.Area;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.PermissionUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.cityselector.util.PinyinUtils;
import com.laoodao.smartagri.view.cityselector.widget.WrapHeightGridView;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 欧源 on 2017/5/3.
 */

public class AreaAdapter extends BaseAdapter<Area> {


    private final int TYPE_LOCATION = 10005;

    private final int TYPE_CONTENT = 10001;

    private final int TYPE_CURRENT = 10000;

    private OnItemCotnentClickListener mOnItemCotnentClickListener;
    private OnItemLocationClickListener mOnItemLocationClickListener;
    private OnAllCountryClickListener mOnAllCountryClickListener;
    private OnItemGridClickListener mOnItemGridClickListener;
    HashMap<String, Integer> mLetterIndexes = new HashMap<>();
    private AreaGridAdapter mGridAdapter;

    private String mLocation;
    private boolean isOpen = false;
    private Area mSelectProvince;
    private Area mSelectCity;
    private Area mSelectCounty;
    private Area mSelectTown;

    private AreaGridAdapter mLocationGridAdapter;
    private int mLevel;

    private String currentTitle;

    public AreaAdapter(Context context, List<Area> objects, int level, String title) {
        super(context, objects);
        int size = objects.size();
        this.mLevel = level;
        this.currentTitle = title;
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
        mGridAdapter = new AreaGridAdapter(context);

        mLocationGridAdapter = new AreaGridAdapter(context);
    }

    public AreaAdapter(Context context, List<Area> objects, int level) {
        super(context, objects);
        int size = objects.size();
        this.mLevel = level;
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
        mGridAdapter = new AreaGridAdapter(context);

        mLocationGridAdapter = new AreaGridAdapter(context);
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

    public void setCurrentArea(int level, List<Area> currentArea) {
        mGridAdapter.addAll(currentArea);
        isOpen = true;
        notifyDataSetChanged();
    }

    public void setSelectCity(Area area) {
        this.mSelectCity = area;
    }

    public Area getSelectProvince() {
        return mSelectProvince;
    }

    public Area getSelectCity() {
        return mSelectCity;
    }

    public Area getSelectCounty() {
        return mSelectCounty;
    }

    public Area getSelectTown() {
        return mSelectTown;
    }

    public void setOnItemGridClickListener(OnItemGridClickListener onItemGridClickListener) {
        this.mOnItemGridClickListener = onItemGridClickListener;
    }

    public void setLocationArea(Pos mPos, List<Area> currentArea) {
        reset();
        mLocationGridAdapter.addAll(currentArea);
        mSelectProvince = new Area();
        mSelectProvince.name = mPos.province.name;
        mSelectProvince.id = mPos.province.i;
        mSelectCity = new Area();
        mSelectCity.name = mPos.city.name;
        mSelectCity.id = mPos.city.i;
        isOpen = false;
        notifyDataSetChanged();
    }

    public void setSelectProvince(Area area) {
        reset();
        this.mSelectProvince = area;
    }


    public void reset() {
        mSelectProvince = null;
        mSelectCity = null;
        mSelectCounty = null;
        mSelectTown = null;
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
                    mSelectProvince = data;
                    mSelectTown = null;
                    mSelectCity = null;
                    mSelectCounty = null;
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
        @BindView(R.id.grid_current)
        WrapHeightGridView mGridCurrent;
        @BindView(R.id.ll_county)
        LinearLayout mLlCounty;
        @BindView(R.id.tv_all_country)
        RoundTextView mTvAllCountry;

        public LocationHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_location);
        }

        @Override
        public void setData(int position, Area data) {
            if (mLevel == 2) {
                mTvAllCountry.setVisibility(View.VISIBLE);
            }
            mGridCurrent.setAdapter(mLocationGridAdapter);

            mGridCurrent.setOnItemClickListener((parent, view, position1, id) -> {

                if (mOnItemGridClickListener != null) {
                    Area item = mLocationGridAdapter.getItem(position1);

                    switch (item.level) {
                        case 2:
                            mSelectCity = item;
                            break;
                        case 3:
                            mSelectCounty = item;
                            break;
                        case 4:
                            mSelectTown = item;
                            break;
                    }
                    mOnItemGridClickListener.onClick(view, position1, item);
                    mLocationGridAdapter.clear();
                }
            });
            if (mLocationGridAdapter.getCount() > 0) {
                mLlCounty.setVisibility(View.VISIBLE);
            } else {
                mLlCounty.setVisibility(View.GONE);
            }
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
            mTvAllCountry.setOnClickListener(v -> {
                if (mOnAllCountryClickListener != null) {
                    mOnAllCountryClickListener.onClick(v);
                }
            });
        }
    }

    class CurrentHolder extends BaseViewHolder<Area> {

        @BindView(R.id.tv_current)
        TextView mTvCurrent;
        @BindView(R.id.btn_choose)
        TextView mBtnChoose;
        @BindView(R.id.grid_county)
        WrapHeightGridView mGridCounty;

        public CurrentHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_current);
        }

        @Override
        public void setData(int position, Area data) {
            mBtnChoose.setVisibility(View.VISIBLE);
            mGridCounty.setAdapter(mGridAdapter);
            mGridCounty.setVisibility(isOpen ? View.VISIBLE : View.GONE);
            mGridCounty.setOnItemClickListener((parent, view, position1, id) -> {
                mLocationGridAdapter.clear();
                if (mOnItemGridClickListener != null) {
                    Area item = mGridAdapter.getItem(position1);
                    switch (item.level) {
                        case 2:
                            mSelectCity = item;
                            break;
                        case 3:
                            mSelectCounty = item;
                            break;
                        case 4:
                            mSelectTown = item;
                            break;
                    }
                    mOnItemGridClickListener.onClick(view, position1, item);
                }
            });
            if (!TextUtils.isEmpty(currentTitle)) {
                mTvCurrent.setText(currentTitle);
            }
            String text = mTvCurrent.getText().toString();
            if (mSelectProvince != null) {
                text = mSelectProvince.name;
                mTvCurrent.setText(text);
//                UiUtils.makeText("================"+mSelectProvince.name);
            }
            if (mSelectCity != null) {
                text += mSelectCity.name;
                mTvCurrent.setText(text);
            }
            if (mSelectCounty != null) {
                text += mSelectCounty.name;
                mTvCurrent.setText(text);
            }
            if (mSelectTown != null) {
                text += mSelectTown.name;
                mTvCurrent.setText(text);
            }

        }

        @OnClick(R.id.btn_choose)
        public void onClick() {
            int visibility = mGridCounty.getVisibility();
            mGridCounty.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
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

    public interface OnItemCurrentClickListener {
        void onClick(View view, int position, Area data);
    }

    public interface OnItemGridClickListener {
        void onClick(View view, int position, Area area);
    }

    public interface OnAllCountryClickListener {
        void onClick(View view);
    }


    public void setOnItemCotnentClickListener(OnItemCotnentClickListener onItemCotnentClickListener) {
        this.mOnItemCotnentClickListener = onItemCotnentClickListener;
    }

    public void setOnItemLocationClickListener(OnItemLocationClickListener onItemLocationClickListener) {
        this.mOnItemLocationClickListener = onItemLocationClickListener;
    }

    public void setOnItemAllCountryClickListener(OnAllCountryClickListener onItemAllCountryClickListener) {
        this.mOnAllCountryClickListener = onItemAllCountryClickListener;
    }

}
