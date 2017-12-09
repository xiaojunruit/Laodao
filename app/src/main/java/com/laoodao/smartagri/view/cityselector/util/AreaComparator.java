package com.laoodao.smartagri.view.cityselector.util;

import com.laoodao.smartagri.db.Area;

import java.util.Comparator;

/**
 * Created by 欧源 on 2017/5/4.
 */

public class AreaComparator implements Comparator<Area> {
    @Override
    public int compare(Area lhs, Area rhs) {
        String a = lhs.pinyin.substring(0, 1);
        String b = rhs.pinyin.substring(0, 1);
        return a.compareTo(b);
    }
}