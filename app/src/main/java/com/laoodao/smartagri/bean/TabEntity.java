package com.laoodao.smartagri.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * Created by Administrator on 2017/3/6.
 * Tablayout实体类
 */

public class TabEntity implements CustomTabEntity {


    private String mTitle;
    private int mSelectedIcon;
    private int mUnselectedIcon;

    public TabEntity(String title, int selectedIcon, int unselectedIcon) {
        this.mTitle = title;
        this.mSelectedIcon = selectedIcon;
        this.mUnselectedIcon = unselectedIcon;
    }

    @Override
    public String getTabTitle() {
        return mTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return mSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return mUnselectedIcon;
    }
}
