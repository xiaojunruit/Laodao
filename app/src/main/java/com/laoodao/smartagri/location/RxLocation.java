package com.laoodao.smartagri.location;

import android.content.Context;

import com.baidu.location.BDLocation;

import rx.Observable;

/**
 * Created by 欧源 on 2017/4/29.
 */

public class RxLocation {

    private static RxLocation instance = new RxLocation();

    private RxLocation () {}

    public static RxLocation get() {
        return instance;
    }

    public Observable<BDLocation> locate(Context context) {
        return Observable.create(new LocationOnSubscribe(context));
    }

    public Observable<BDLocation> locateLastKnown(Context context) {
        return Observable.create(new LocationLateKnownOnSubscribe(context));
    }

}
