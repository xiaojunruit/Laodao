package com.laoodao.smartagri.ui.discovery.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.search.share.LocationShareURLOption;
import com.baidu.mapapi.search.share.OnGetShareUrlResultListener;
import com.baidu.mapapi.search.share.RouteShareURLOption;
import com.baidu.mapapi.search.share.ShareUrlResult;
import com.baidu.mapapi.search.share.ShareUrlSearch;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.NearbyShopDetail;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.ui.discovery.contract.NearbyShopMapContract;
import com.laoodao.smartagri.ui.discovery.dialog.CallMapDialog;
import com.laoodao.smartagri.ui.discovery.presenter.NearbyShopMapPresenter;
import com.laoodao.smartagri.utils.GPSUtil;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.OverlayManager;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.utils.WalkingRouteOverlay;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by WORK on 2017/8/9.
 */

public class NearbyShopMapActivity extends BaseActivity<NearbyShopMapPresenter> implements NearbyShopMapContract.NearbyShopMapView, SensorEventListener, OnGetRoutePlanResultListener, OnGetShareUrlResultListener {
    @BindView(R.id.bmapView)
    MapView mBmapView;
    @BindView(R.id.ic_location)
    ImageView mIcLocation;
    @BindView(R.id.tv_error)
    TextView mTvError;
    @BindView(R.id.iv_call_map)
    ImageView mIvCallMap;
    @BindView(R.id.iv_shop)
    ImageView mIvShop;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    private BaiduMap mBaiduMap;
    private ImageView button;

    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private int mCurrentDirection = 0;
    boolean isFirstLoc = true;
    private MyLocationData locData;
    private SensorManager mSensorManager;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    private Double lastX = 0.0;
    LocationClient mLocClient;
    private MapStatus.Builder builder;

    RoutePlanSearch mSearch = null;
    private LatLng loc_start;
    private LatLng loc_end;
    private CallMapDialog callMapDialog;
    private NearbyShopDetail mDetail;
    private ShareUrlSearch mShareUrlSearch = null;
    private RouteShareURLOption.RouteShareMode mRouteShareMode;
    private String address="";


    public static void start(Context context, NearbyShopDetail detail) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("detail", detail);
        UiUtils.startActivity(context, NearbyShopMapActivity.class, bundle);
    }


    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nearby_shop_map;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        mDetail = (NearbyShopDetail) getIntent().getSerializableExtra("detail");
        if (mDetail != null) {
            Glide.with(this).load(mDetail.logo).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.mipmap.bg_seize_seat).error(R.mipmap.bg_seize_seat).into(mIvShop);
            mTvName.setText(mDetail.storeName);
            mTvAddress.setText(mDetail.storeAddr);
        }
        mShareUrlSearch = ShareUrlSearch.newInstance();
        mShareUrlSearch.setOnGetShareUrlResultListener(this);
        mRouteShareMode = RouteShareURLOption.RouteShareMode.FOOT_ROUTE_SHARE_MODE;
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        mBaiduMap = mBmapView.getMap();

        mLocClient = new LocationClient(this);
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        mBmapView.showZoomControls(false);
        mBmapView.setPadding(0, 0, 0, UiUtils.dip2px(20));

        Observable.interval(0, 1000, TimeUnit.MILLISECONDS)
                .compose(this.<Long>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        requestLocation();
                    }
                });

    }


    private void requestLocation() {
        RxLocation.get().locate(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LocationSubscriber() {
                    @Override
                    public void onLocatedSuccess(@NonNull BDLocation location) {
                        if (location == null || mBaiduMap == null) {
                            return;
                        }
                        address=location.getAddress().address;
                        mCurrentLat = location.getLatitude();
                        mCurrentLon = location.getLongitude();
                        mCurrentAccracy = location.getRadius();
                        locData = new MyLocationData.Builder()
                                .accuracy(location.getRadius())
                                // 此处设置开发者获取到的方向信息，顺时针0-360
                                .direction(mCurrentDirection).latitude(location.getLatitude())
                                .longitude(location.getLongitude()).build();
                        mBaiduMap.setMyLocationData(locData);
                        if (isFirstLoc) {
                            isFirstLoc = false;
                            mBaiduMap.setMyLocationEnabled(true);
                            LatLng ll = new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            builder = new MapStatus.Builder();
                            builder.target(ll).zoom(18.0f);
                            mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                                    mCurrentMode, true, null));
                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                            loc_start = new LatLng(location.getLatitude(), location.getLongitude());
                            loc_end = new LatLng(Double.parseDouble(mDetail.lat), Double.parseDouble(mDetail.lng));
                            startSearch(loc_start, loc_end);
                        }
                    }

                    @Override
                    public void onLocatedFail(BDLocation bdLocation) {

                    }
                });
    }


    private void startSearch(LatLng start, LatLng end) {
        PlanNode stNode = PlanNode.withLocation(start);
        PlanNode enNode = PlanNode.withLocation(end);
        mSearch.walkingSearch((new WalkingRoutePlanOption()).from(stNode).to(enNode));
    }

    @Override
    protected void onDestroy() {
        mLocClient.stop();
        BaiduMapNavigation.finish(this);
        MapView.setMapCustomEnable(false);
        mBmapView.onDestroy();
        mBmapView = null;
        if (mSearch != null) {
            mSearch.destroy();
        }
        mShareUrlSearch.destroy();
        super.onDestroy();
    }

    @OnClick({R.id.ic_location, R.id.tv_error, R.id.iv_call_map})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_call_map:
                if (callMapDialog == null) {
                    callMapDialog = new CallMapDialog(this);
                }
                callMapDialog.show();
                callMapDialog.setOnCallBaiDuListener(() -> {
//                    startWalkingNavi();
                    startMap(true);
                });
                callMapDialog.setOnCallGaoDeListener(() -> {
                    startMap(false);
                });
                callMapDialog.setOnCallShareListener(() -> {
                    share();
                });
                break;
            case R.id.ic_location:
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(builder.build());
                mBaiduMap.animateMapStatus(mapStatusUpdate);
                break;
            case R.id.tv_error:
                MapReportingErrorsActivity.start(this, Integer.parseInt(mDetail.storeId));
                break;
        }
    }

    @Override
    protected void onResume() {
        mBmapView.onResume();
        super.onResume();
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBmapView.onPause();
    }

    @Override
    protected void onStop() {
        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    int nodeIndex = -1;
    WalkingRouteResult nowResultwalk = null;
    boolean hasShownDialogue = false;
    RouteLine route = null;
    OverlayManager routeOverlay = null;

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("检索地址有歧义，请重新设置。\n可通过getSuggestAddrInfo()接口获得建议查询信息");
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//            nodeIndex = -1;
            route = result.getRouteLines().get(0);
            WalkingRouteOverlay overlay = new MyWalkingRouteOverlay(mBaiduMap);
            mBaiduMap.setOnMarkerClickListener(overlay);
            routeOverlay = overlay;
            overlay.setData(result.getRouteLines().get(0));
            overlay.addToMap();
            overlay.zoomToSpan();


        }
    }

    @Override
    public void onGetPoiDetailShareUrlResult(ShareUrlResult result) {
        // 分享短串结果
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, "您的朋友通过百度地图SDK与您分享一个POI点详情: " + mDetail.storeName
                + " -- " + result.getUrl());
        it.setType("text/plain");
        startActivity(Intent.createChooser(it, "将短串分享到"));
    }

    @Override
    public void onGetLocationShareUrlResult(ShareUrlResult result) {
        // 分享短串结果
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, "<font color='#333646'>您的朋友通过百度地图SDK与您分享一个位置:</font> " + mDetail.storeName
                + " -- " + result.getUrl());
        it.setType("text/plain");
        startActivity(Intent.createChooser(it, "将短串分享到"));
    }

    @Override
    public void onGetRouteShareUrlResult(ShareUrlResult shareUrlResult) {
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_TEXT, mDetail.storeName
                + " -- " + shareUrlResult.getUrl());
        it.setType("text/plain");
        startActivity(Intent.createChooser(it, "将短串分享到"));
    }


    private class MyWalkingRouteOverlay extends WalkingRouteOverlay {

        public MyWalkingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            return null;
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            return BitmapDescriptorFactory.fromResource(R.mipmap.ic_shop);
        }
    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }


//    public void startWalkingNavi() {
//        // 构建 导航参数
//        NaviParaOption para = new NaviParaOption()
//                .startPoint(loc_start).endPoint(loc_end);
//
//        try {
//            BaiduMapNavigation.openBaiduMapWalkNavi(para, this);
//        } catch (BaiduMapAppNotSupportNaviException e) {
//            e.printStackTrace();
//        }
//
//    }


    //高德地图,起点就是定位点---直接导航
    // 终点是LatLng ll = new LatLng("你的纬度latitude","你的经度longitude");
    public void startNaviGao() {
        if (isInstalled(this, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=appa&poiname=万江中学&lat=" + 23.038333 + "&lon=" + 113.710211 + "&dev=0");
                startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            UiUtils.makeText("您尚未安装高德地图或地图版本过低");
        }
    }

    public void startMap(boolean baidu) {
        Intent intent = new Intent();
        if (baidu) {
            if (isInstalled(NearbyShopMapActivity.this, "com.baidu.BaiduMap")) {
                try {
                    intent = Intent.parseUri("intent://map/direction?" +
                            "origin=latlng:" + loc_start.latitude + "," + loc_start.longitude +
                            "|name:" + address +
                            "&destination=latlng:" + loc_end.latitude + "," + loc_end.longitude +
                            "|name:" + mDetail.storeAddr +
                            "&mode=driving" +
                            "&src=Name|AppName" +
                            "#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end", 0);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            } else {
                UiUtils.makeText("您尚未安装百度地图或地图版本过低");
            }
        } else {
            if (isInstalled(NearbyShopMapActivity.this, "com.autonavi.minimap")) {
                intent.setData(Uri
                        .parse("androidamap://route?" +
                                "sourceApplication=softname" +
                                "&slat=" + GPSUtil.bd09_To_Gcj02(mCurrentLat, mCurrentLon)[0] +
                                "&slon=" + GPSUtil.bd09_To_Gcj02(mCurrentLat, mCurrentLon)[1] +
                                "&dlat=" + GPSUtil.bd09_To_Gcj02(Double.parseDouble(mDetail.lat), Double.parseDouble(mDetail.lng))[0] +
                                "&dlon=" + GPSUtil.bd09_To_Gcj02(Double.parseDouble(mDetail.lat), Double.parseDouble(mDetail.lng))[1] +
                                "&dname=" + mDetail.storeAddr +
                                "&dev=0" +
                                "&m=0" +
                                "&t=4"));
                startActivity(intent);
            } else {
                UiUtils.makeText("您尚未安装高德地图或地图版本过低");
            }
        }
    }


    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public boolean isInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String pkName = packageInfos.get(i).packageName;
                if (pkName.equals(packageName)) return true;
            }
        }
        return false;
    }


    private void share() {
        PlanNode startNode = PlanNode.withLocation(loc_start);
        PlanNode enPlanNode = PlanNode.withLocation(loc_end);
        mShareUrlSearch.requestRouteShareUrl(new RouteShareURLOption()
                .from(startNode).to(enPlanNode).routMode(mRouteShareMode));
    }

}
