package com.laoodao.smartagri;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.laoodao.smartagri.bean.CottonCookie;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.event.SelectAreaChangeEvent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.utils.ACache;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by WORK on 2017/4/21.
 */

public final class Global {


    private static Global mInstance;
    private String _token = "";
    private boolean _isLoggedIn = false;

    public int getLastVersionCode() {
        return SharedPreferencesUtil.getInstance().getInt(Constant.LAST_VERSION_CODE, 0);
    }

    public void setLastVersionCode(int lastVersionCode) {
        SharedPreferencesUtil.getInstance().putInt(Constant.LAST_VERSION_CODE, lastVersionCode);
    }

    public static Global getInstance() {
        if (mInstance == null) {
            synchronized (Global.class) {
                if (mInstance == null) {
                    mInstance = new Global();
                }
            }
        }
        return mInstance;
    }


    public Global setUserInfo(User user) {
        SharedPreferencesUtil.getInstance().putObject(Constant.USER_INFO, user);
        _isLoggedIn = true;
        return this;
    }

    public Global markUserInfoChange() {
        EventBus.getDefault().post(new UserInfoChangedEvent());
        return this;
    }


    public User getUserInfo() {
        return SharedPreferencesUtil.getInstance().getObject(Constant.USER_INFO);
    }


    public void init(Context context) {
        SharedPreferencesUtil.init(context,
                context.getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);


        User user = getUserInfo();
        String token = SharedPreferencesUtil.getInstance().getString(Constant.TOKEN);
        if (user != null && !TextUtils.isEmpty(token)) {
            _isLoggedIn = true;
            _token = token;
        }
    }


    public boolean isFollowPlant() {
        User user = getUserInfo();
        return user != null && user.followPlant != null && user.followPlant.size() > 0;
    }


    public SelectLocation getSelectLocation() {

        return SharedPreferencesUtil.getInstance().getObject(Constant.SELECTED_CITY);
    }


    /**
     * 获取token
     *
     * @return
     */
    public String getToken() {
        return _token;
    }

    public boolean isLoggedIn() {
        return _isLoggedIn;
    }


    public BDLocation getBdLocation() {
        return SharedPreferencesUtil.getInstance().getObject(Constant.SELECTED_CITY);
    }

    public void setCottonCookie(CottonCookie cottonCookie) {
        SharedPreferencesUtil.getInstance().putObject(Constant.COTTON, cottonCookie);
    }

    public CottonCookie getCottonCookie() {
        return SharedPreferencesUtil.getInstance().getObject(Constant.COTTON);
    }

    /**
     * 当前选择的地区
     *
     * @param selectLocation
     */
    public void setSelectLocation(SelectLocation selectLocation) {
        SharedPreferencesUtil.getInstance().putObject(Constant.SELECTED_CITY, selectLocation);
        EventBus.getDefault().post(new SelectAreaChangeEvent());
    }

    /**
     * 当前定位信息
     */
    public void setCurrentLocation(BDLocation bdLocation) {
        SelectLocation selectLocation = new SelectLocation();
        selectLocation.areaId =null;
        selectLocation.longitude = String.valueOf(bdLocation.getLongitude());
        selectLocation.latitude = String.valueOf(bdLocation.getLatitude());
        selectLocation.cityName = bdLocation.getCity();
        SharedPreferencesUtil.getInstance().putObject(Constant.CURRENT_LOCATION, selectLocation);
    }

    /**
     * 获取当前定位
     */
    public SelectLocation getCurrentLocation() {
        return SharedPreferencesUtil.getInstance().getObject(Constant.CURRENT_LOCATION);
    }


    /**
     * 退出登录
     */
    public void logout() {
        SharedPreferencesUtil.getInstance().remove(Constant.USER_INFO).remove(Constant.TOKEN);
        _isLoggedIn = false;
        _token = "";
        ACache.get(LDApplication.getInstance()).clear();
        EventBus.getDefault().post(new UserInfoChangedEvent());
    }


    public void setToken(String token) {
        _token = token;
        SharedPreferencesUtil.getInstance().putString(Constant.TOKEN, token);
    }
}
