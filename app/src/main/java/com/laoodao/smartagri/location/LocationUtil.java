package com.laoodao.smartagri.location;

import com.baidu.location.BDLocation;

/**
 * Created by 欧源 on 2017/4/29.
 */

public class LocationUtil {
    public static boolean isLocationResultEffective(BDLocation bdLocation) {
        return bdLocation != null
                && (bdLocation.getLocType() == BDLocation.TypeGpsLocation
                || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation);
    }
}
