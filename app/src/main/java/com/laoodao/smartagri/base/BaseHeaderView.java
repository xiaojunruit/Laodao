package com.laoodao.smartagri.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.laoodao.smartagri.utils.KnifeUtil;

/**
 * Created by 欧源 on 2017/4/27.
 */

public abstract class BaseHeaderView {
    protected View mHeaderView;

    public BaseHeaderView(Context context) {
        mHeaderView = LayoutInflater.from(context).inflate(getLayoutHeaderViewId(), null, false);
        KnifeUtil.bindTarget(this, mHeaderView);
    }

    protected abstract int getLayoutHeaderViewId();

}
