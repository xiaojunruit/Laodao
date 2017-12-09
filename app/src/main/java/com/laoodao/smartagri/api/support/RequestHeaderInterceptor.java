package com.laoodao.smartagri.api.support;

import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.laoodao.smartagri.BuildConfig;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.bean.CottonCookie;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.LogUtil;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.laoodao.smartagri.utils.UiUtils.getContext;

/**
 * Created by 欧源 on 2017/4/22.
 */

public class RequestHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request.Builder request = chain.request().newBuilder();

        request.addHeader("Accept", "application/json; q=0.5");
        String UA = "laoodao/" + BuildConfig.VERSION_NAME +
                " (Linux; U; Android " +
                Build.VERSION.RELEASE + "; " +
                Locale.getDefault().getLanguage() + "-" +
                Locale.getDefault().getCountry().toLowerCase() + ")";
        request.addHeader("User-Agent", UA);
        boolean loggedIn = Global.getInstance().isLoggedIn();
//        request.addHeader("X-TOKEN", "da7d55fd52fe0a0732d552dbcf7ff8c1");
        if (loggedIn) {
            request.addHeader("X-TOKEN", Global.getInstance().getToken());
        }
        PackageManager packageManager = getContext().getPackageManager();

        boolean hasPermission = PackageManager.PERMISSION_GRANTED == packageManager.checkPermission("android.permission.READ_PHONE_STATE", getContext().getPackageName());
        /**
         * 判断是否有权限
         */
        if (hasPermission) {
            request.addHeader("X-DEVICEID", DeviceUtils.getIMEI(getContext()));
        }
        SelectLocation currentLocation = Global.getInstance().getCurrentLocation();
        if (currentLocation != null) {
            request.addHeader("X-LON", currentLocation.longitude + "");
            request.addHeader("X-LAT", currentLocation.latitude + "");

        }
        CottonCookie cottonCookie = Global.getInstance().getCottonCookie();
        if (cottonCookie != null) {
            if (cottonCookie.cookidValue != null) {
                request.addHeader("Cookie", cottonCookie.cookidValue + "");
            }
        }

        /**
         * 当前选择的城市
         */
        SelectLocation selectLocation = Global.getInstance().getSelectLocation();
        if (selectLocation != null) {
            request.addHeader("city", selectLocation.areaId + "");
        }

        return chain.proceed(request.build());
    }
}
