package com.laoodao.smartagri.location;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by 欧源 on 2017/4/29.
 */

public class LocationOnSubscribe implements Observable.OnSubscribe<BDLocation> {

    private final Context context;

    public LocationOnSubscribe(Context context) {
        this.context = context;
    }

    @Override
    public void call(Subscriber<? super BDLocation> subscriber) {
        BDLocationListener bdLocationListener = new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                subscriber.onNext(bdLocation);
                subscriber.onCompleted();
            }

            @Override
            public void onConnectHotSpotMessage(String s, int i) {

            }
        };
        LocationClient.get(context).locate(bdLocationListener);
    }
}
