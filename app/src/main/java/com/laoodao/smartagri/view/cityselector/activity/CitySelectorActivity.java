package com.laoodao.smartagri.view.cityselector.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.SelectLocation;
import com.laoodao.smartagri.db.Area;
import com.laoodao.smartagri.db.Area_Table;
import com.laoodao.smartagri.event.CityEvent;
import com.laoodao.smartagri.event.LocationChangeEvent;
import com.laoodao.smartagri.event.NearbyShopEvent;
import com.laoodao.smartagri.event.SelectAreaChangeEvent;
import com.laoodao.smartagri.location.LocationSubscriber;
import com.laoodao.smartagri.location.RxLocation;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.SharedPreferencesUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.cityselector.adapter.CityAdapter;
import com.laoodao.smartagri.view.cityselector.adapter.ResultAdapter;
import com.laoodao.smartagri.view.cityselector.util.AreaComparator;
import com.laoodao.smartagri.view.cityselector.util.PinyinUtils;
import com.laoodao.smartagri.view.cityselector.widget.SideLetterBar;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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

public class CitySelectorActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private RecyclerView mRecyclerView;
    private RecyclerView mResultRecyclerView;
    private RoundTextView mOverlay;
    private SideLetterBar mSideLetterBar;
    private CityAdapter mCityAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private ImageView mIvBack;
    private EditText mEtSearch;
    private ResultAdapter mResultAdapter;
    private TextView mTvNoResult;
    private String mCurrentCity;

    public static void start(Context context, String currentCity) {
        Bundle bundle = new Bundle();
        bundle.putString("currentCity", currentCity);
        UiUtils.startActivity(context, CitySelectorActivity.class, bundle);
    }

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
        mCurrentCity = getIntent().getStringExtra("currentCity");
        initSideLetterBar();
        initRecyclerView();
        initLocation();
    }

    /*
   初始化定位信息
     */
    private void initLocation() {
        RxLocation.get().locate(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LocationSubscriber() {
                    @Override
                    public void onLocatedSuccess(@NonNull BDLocation bdLocation) {
                        Global.getInstance().setCurrentLocation(bdLocation);
                        mCityAdapter.updateLocation(bdLocation.getCity());
                    }

                    @Override
                    public void onLocatedFail(BDLocation bdLocation) {
                    }
                });
    }

    private void initRecyclerView() {
        List<Area> areas = SQLite.select().from(Area.class).where(Area_Table.level.eq(2)).queryList();
        Collections.sort(areas, new AreaComparator());
        mCityAdapter = new CityAdapter(this, areas, mCurrentCity);
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
         * 列表城市点击
         */
        mCityAdapter.setOnItemCotnentClickListener((view, position, data) -> {
            clickCity(data.id, data.name);
        });

        /**
         * 定位点击
         */
        mCityAdapter.setOnItemLocationClickListener((view, location) -> {
            SelectLocation currentLocation = Global.getInstance().getCurrentLocation();
            Global.getInstance().setSelectLocation(currentLocation);
            finish();
        });

        //resultRecyclerView
        mResultAdapter = new ResultAdapter(this);
        mResultRecyclerView.setAdapter(mResultAdapter);

        mResultAdapter.setOnItemClickListener(position -> {
            Area item = mResultAdapter.getItem(position);
            clickCity(item.id, item.name);
        });
    }

    private void clickCity(long id, String name) {
        SelectLocation location = new SelectLocation();
        location.cityName = name;
        location.latitude = null;
        location.longitude = null;
        location.areaId = String.valueOf(id);
        Global.getInstance().setSelectLocation(location);
        finish();
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
                                        .and(Area_Table.level.eq(2))
                                        .or(Area_Table.pinyin.like("%" + text + "%"))
                                        .and(Area_Table.level.eq(2))
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
