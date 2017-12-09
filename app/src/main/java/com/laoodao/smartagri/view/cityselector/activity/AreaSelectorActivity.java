package com.laoodao.smartagri.view.cityselector.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.LDApplication;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.api.service.ServiceManager;
import com.laoodao.smartagri.bean.Pos;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.db.Area;
import com.laoodao.smartagri.db.Area_Table;
import com.laoodao.smartagri.event.SelectAreaEvent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.utils.DeviceUtils;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.cityselector.adapter.AreaAdapter;
import com.laoodao.smartagri.view.cityselector.adapter.ResultAdapter;
import com.laoodao.smartagri.view.cityselector.util.AreaComparator;
import com.laoodao.smartagri.view.cityselector.util.PinyinUtils;
import com.laoodao.smartagri.view.cityselector.widget.SideLetterBar;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 欧源 on 2017/5/2.
 */

public class AreaSelectorActivity extends RxAppCompatActivity implements View.OnClickListener, TextWatcher {

    private RecyclerView mRecyclerView;
    private RecyclerView mResultRecyclerView;
    private RoundTextView mOverlay;
    private SideLetterBar mSideLetterBar;
    private AreaAdapter mCityAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView mIvBack;
    private EditText mEtSearch;
    private ResultAdapter mResultAdapter;
    private TextView mTvNoResult;
    private String mLat = "";
    private String mLon = "";
    public static final int REQUEST_CODE = 100004;
    public int mLevel = 5;
    public int mRequestCode;
    ServiceManager mServiceManager;
    private String title;
    private Pos mPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selector);
        mResultRecyclerView = (RecyclerView) findViewById(R.id.result_recyclerview);
        mSideLetterBar = (SideLetterBar) findViewById(R.id.side);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mOverlay = (RoundTextView) findViewById(R.id.tv_overlay);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mEtSearch = (EditText) findViewById(R.id.et_search);
        mTvNoResult = (TextView) findViewById(R.id.tv_no_result);
        mEtSearch.addTextChangedListener(this);
        mIvBack.setOnClickListener(this);
        mLevel = getIntent().getIntExtra("LEVEL", 5);
        mRequestCode = getIntent().getIntExtra("REQUEST_CODE", 0);
        title = getIntent().getStringExtra("TITLE");
        LDApplication application = (LDApplication) getApplication();
        mServiceManager = application.getAppComponent().getServiceManager();
        initSideLetterBar();
        initRecyclerView();
        initLocation();
    }


    public static void start(Context context, int level, int requestCode) {

        Bundle bundle = new Bundle();
        bundle.putInt("REQUEST_CODE", requestCode);
        bundle.putInt("LEVEL", level);
        UiUtils.startActivity(context, AreaSelectorActivity.class, bundle);
    }


    public static void start(Context context, int level, int requestCode, String title) {

        Bundle bundle = new Bundle();
        bundle.putInt("REQUEST_CODE", requestCode);
        bundle.putInt("LEVEL", level);
        bundle.putString("TITLE", title);
        UiUtils.startActivity(context, AreaSelectorActivity.class, bundle);
    }

    /*
   初始化定位信息
     */
    private void initLocation() {
        RxLocation.get().locate(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(new LocationSubscriber() {
                    @Override
                    public void onLocatedSuccess(@NonNull BDLocation bdLocation) {

                        mLat = String.valueOf(bdLocation.getLatitude());
                        mLon = String.valueOf(bdLocation.getLongitude());
                        mServiceManager.getHomeService().getPos(mLon + "," + mLat)
                                .compose(bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Result<Pos>>() {
                                    @Override
                                    public void onCompleted() {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onNext(Result<Pos> result) {
                                        mCityAdapter.updateLocation(bdLocation.getCity());
                                        mPos = result.data;
                                    }
                                });
                    }

                    @Override
                    public void onLocatedFail(BDLocation bdLocation) {
                    }
                });
    }

    private void initRecyclerView() {
        List<Area> areas = SQLite.select().from(Area.class).where(Area_Table.level.eq(1)).queryList();
        Collections.sort(areas, new AreaComparator());
        mCityAdapter = new AreaAdapter(this, areas, mLevel, title);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mCityAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVerticalScrollBarEnabled(false);
        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        /**
         * 滑动recyclerview,显示Overlay
         */
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                Area item = mCityAdapter.getItem(firstVisibleItemPosition);
                String firstLetter = PinyinUtils.getFirstLetter(item.pinyin);
                mSideLetterBar.showOverlay(firstLetter);
            }
        });
        /**
         * 省份点击事件
         */
        mCityAdapter.setOnItemCotnentClickListener((view, position, data) -> {
            List<Area> currentArea = SQLite.select().from(Area.class).where(Area_Table.pid.eq(data.id)).queryList();
            if (mLevel == 2) {
                currentArea.add(0, data);
            }
            mCityAdapter.setCurrentArea(data.level + 1, currentArea);
            mLinearLayoutManager.scrollToPositionWithOffset(0, 1);
        });
        /**
         * 下一级点击事件
         */
        mCityAdapter.setOnItemGridClickListener((view, position, area) -> {
            if (area.level == 1) {
                SelectAreaEvent event = new SelectAreaEvent(null, area, null, null);
                event.requestCode = mRequestCode;
                EventBus.getDefault().post(event);
                finish();
                return;

            }
            List<Area> nextLevel = SQLite.select().from(Area.class).where(Area_Table.pid.eq(area.id)).queryList();
            if (nextLevel != null && !nextLevel.isEmpty() && mLevel > area.level) {
                mCityAdapter.setCurrentArea(area.level + 1, nextLevel);
            } else {
                /**
                 * 没有下一级地区了，直接返回数据
                 */
                SelectAreaEvent event = new SelectAreaEvent(mCityAdapter.getSelectProvince(),
                        mCityAdapter.getSelectCity(),
                        mCityAdapter.getSelectCounty(),
                        mCityAdapter.getSelectTown());
                event.requestCode = mRequestCode;
                EventBus.getDefault().post(event);
                finish();
            }
        });
        /**
         *全国点击
         */
        mCityAdapter.setOnItemAllCountryClickListener(view -> {
            SelectAreaEvent event = new SelectAreaEvent(null, null, null, null);
            event.requestCode = mRequestCode;
            EventBus.getDefault().post(event);
            finish();
        });

        mCityAdapter.setOnItemLocationClickListener((view, location) -> {
            if (mPos == null) {
                return;
            }
            /**
             *
             */
            if (mLevel == 2) {
                Area area = new Area();
                area.id = mPos.city.i;
                area.name = mPos.city.name;
                SelectAreaEvent event = new SelectAreaEvent(null, area, null, null);
                event.requestCode = mRequestCode;
                EventBus.getDefault().post(event);
                finish();
                return;
            }
            List<Area> currentCity = SQLite.select().from(Area.class).where(Area_Table.pid.eq(mPos.province.i)).queryList();
            mCityAdapter.setCurrentArea(mPos.city.i + 1, currentCity);

            List<Area> currentArea = SQLite.select().from(Area.class).where(Area_Table.pid.eq(mPos.city.i)).queryList();
            mCityAdapter.setLocationArea(mPos, currentArea);
        });

        //resultRecyclerView
        mResultAdapter = new ResultAdapter(this);
        mResultRecyclerView.setAdapter(mResultAdapter);

        mResultAdapter.setOnItemClickListener(position -> {
            Area data = mResultAdapter.getItem(position);
            if (data.level == 1) {
                //省份
                List<Area> currentArea = SQLite.select().from(Area.class).where(Area_Table.pid.eq(data.id)).queryList();
                if (mLevel == 2) {
                    currentArea.add(0, data);
                }
                mCityAdapter.setSelectProvince(data);
                mCityAdapter.setCurrentArea(data.level + 1, currentArea);
            } else {
                if (mLevel == 2) {
//                    Area area = new Area();
//                    area.id = mPos.city.i;
//                    area.name = mPos.city.name;
                    SelectAreaEvent event = new SelectAreaEvent(null, data, null, null);
                    event.requestCode = mRequestCode;
                    EventBus.getDefault().post(event);
                    finish();
                    return;
                }


                //市
                Area province = SQLite.select().from(Area.class).where(Area_Table.id.eq(data.pid)).querySingle();
                mCityAdapter.setSelectProvince(province);
                mCityAdapter.setSelectCity(data);
                //市
                List<Area> currentArea = SQLite.select().from(Area.class).where(Area_Table.pid.eq(data.id)).queryList();

                mCityAdapter.setCurrentArea(data.level + 1, currentArea);
            }


            mLinearLayoutManager.scrollToPositionWithOffset(0, 1);
            mEtSearch.setText(null);
            DeviceUtils.hideSoftKeyboard(this, mEtSearch);
        });


    }


    private void getLocationInfo() {


    }


    private void initSideLetterBar() {
        mSideLetterBar.setOnLetterChangedListener(mOnLetterListener);
        mSideLetterBar.setOverlay(mOverlay);
    }

    /**
     * 侧边滑动
     */
    SideLetterBar.OnLetterChangedListener mOnLetterListener = letter -> {
        int position = mCityAdapter.getLetterPosition(letter);
        if (position != -1)
            mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
    };

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String keyword = mEtSearch.getText().toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            mResultRecyclerView.setVisibility(View.GONE);
            mTvNoResult.setVisibility(View.GONE);
        } else {
            doSearch(keyword);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 在子线程中执行查询语句，不然会卡
     *
     * @param text
     */
    private void doSearch(String text) {

        Observable
                .create(new Observable.OnSubscribe<List<Area>>() {
                    @Override
                    public void call(Subscriber<? super List<Area>> subscriber) {
                        List<Area> areas =
                                SQLite.select().from(Area.class)
                                        .where(Area_Table.name.like("%" + text + "%"))
                                        .and(Area_Table.level.lessThan(3))
                                        .or(Area_Table.pinyin.like("%" + text + "%"))
                                        .and(Area_Table.level.lessThan(3))
                                        .queryList();
                        subscriber.onNext(areas);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(areas -> {
                    if (!(areas == null || areas.isEmpty())) {
                        return true;
                    } else {
                        mTvNoResult.setVisibility(View.VISIBLE);
                        mResultRecyclerView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .subscribe(
                        areas -> {
                            mTvNoResult.setVisibility(View.GONE);
                            mResultAdapter.addAll(areas, true);
                            mResultRecyclerView.setVisibility(View.VISIBLE);
                        }
                        , throwable -> {
                            UiUtils.makeText(throwable.getMessage());
                            mResultRecyclerView.setVisibility(View.GONE);
                            mTvNoResult.setVisibility(View.GONE);
                        }
                );
    }
}
