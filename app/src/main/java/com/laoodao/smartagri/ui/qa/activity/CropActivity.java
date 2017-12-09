package com.laoodao.smartagri.ui.qa.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.Crop;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.CropSelectEvent;
import com.laoodao.smartagri.ui.qa.adapter.CropAdapter;
import com.laoodao.smartagri.ui.qa.adapter.CropCategoryAdapter;
import com.laoodao.smartagri.ui.qa.adapter.SelectedCropAdapter;
import com.laoodao.smartagri.ui.qa.contract.CropContract;
import com.laoodao.smartagri.ui.qa.presenter.CropPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.laoodao.smartagri.utils.UiUtils.getString;


public class CropActivity extends BaseActivity<CropPresenter> implements CropContract.CropView {


    @BindView(R.id.left_recyclerview)
    RecyclerView mLeftRecyclerView;
    @BindView(R.id.tv_category)
    TextView mTvCategory;
    @BindView(R.id.right_recyclerview)
    RecyclerView mRightRecyclerView;
    @BindView(R.id.select_recyclerview)
    RecyclerView mSelectRecyclerView;
    @BindView(R.id.tv_selected_count)
    RoundTextView mTvSelectedCount;
    @BindView(R.id.search_list)
    RecyclerView mSearchList;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    //作物分类
    private CropCategoryAdapter mCategoryAdapter;
    //作物
    private CropAdapter mCropAdapter;
    //选中的作物
    private SelectedCropAdapter mSelectedCropAdapter;
    //搜搜作物
    private CropAdapter mSearchCropAdapter;


    public static final String REQUEST_REUSLT = "REQUEST_RESULT";
    public final static int REQUEST_CODE = 1000;
    private int mType = 0;
    private List<Plant> mFollowPlant;
    int maxSelect = 5;
    private boolean mNeedMax = true;

    public static void start(Activity activity, List<Plant> followPlant) {
        Intent intent = new Intent(activity, CropActivity.class);

        intent.putExtra("FOLLOW_PLANT", (Serializable) followPlant);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }


    public static void start(Context context, int type, List<Plant> followPlant) {
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", type);
        bundle.putSerializable("FOLLOW_PLANT", (Serializable) followPlant);
        UiUtils.startActivity(context, CropActivity.class, bundle);
    }

    public static void start(Activity activity, int type, List<Plant> followPlant, boolean isNeedMax) {
        Intent intent = new Intent(activity, CropActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("TYPE", type);
        bundle.putBoolean("IS_NEEDMAX", isNeedMax);
        bundle.putSerializable("FOLLOW_PLANT", (Serializable) followPlant);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerQAComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_crop;
    }

    @Override
    protected void configViews() {
//        mToolbar.setNavigationIcon(null);
//        setSupportActionBar(mToolbar);
        mType = getIntent().getIntExtra("TYPE", 0);
        mNeedMax = getIntent().getBooleanExtra("IS_NEEDMAX", true);
        mFollowPlant = (List<Plant>) getIntent().getSerializableExtra("FOLLOW_PLANT");
        if (mFollowPlant != null) {
            mTvSelectedCount.setText(String.valueOf(mFollowPlant.size()));
        }
        initRecyclerView();
        mPresenter.getCropList();
        searchList();
    }

    private void searchList() {
        mEtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mSearchList.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
                if (s.length() > 0) {
                    mPresenter.getSearchCropList(1, s.toString());
                }
            }
        });
    }

    private void initRecyclerView() {
        //作物
        mSearchCropAdapter = new CropAdapter(this);
        mCropAdapter = new CropAdapter(this);
        mRightRecyclerView.setAdapter(mCropAdapter);
        mSearchList.setLayoutManager(new LinearLayoutManager(this));
        mSearchList.setAdapter(mSearchCropAdapter);

        mCropAdapter.setOnAddCropClickListener((position, item) -> {
            selectCrop(position, item);
        });

        mSearchCropAdapter.setOnAddCropClickListener((position, item) -> {
            selectCrop(position, item);
        });

        //分类
        mCategoryAdapter = new CropCategoryAdapter(this);
        mLeftRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemChangeSelectListener((position, item) -> {
            mTvCategory.setText(item.name);
            mCropAdapter.addAll(item.plant, true);
        });

        //选中的作物
        mSelectRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mSelectedCropAdapter = new SelectedCropAdapter(this);
        mSelectRecyclerView.setAdapter(mSelectedCropAdapter);
        mSelectedCropAdapter.setOnItemClickListener(position -> {
            //移除所选中的作物，并改变选中状态


            Plant item = mSelectedCropAdapter.getItem(position);
            mCropAdapter.cancelSelect(item.id);
            mSearchCropAdapter.cancelSelect(item.id);
            //移除
            mSelectedCropAdapter.remove(position);
            int itemCount = mSelectedCropAdapter.getItemCount();
            mTvSelectedCount.setText(String.valueOf(itemCount));
        });


    }

    /**
     * 选择作物
     *
     * @param position
     * @param item
     */
    private void selectCrop(int position, Plant item) {
        if (mNeedMax) {
            if (mSelectedCropAdapter.getItemCount() >= maxSelect) {
                UiUtils.makeText(getString(R.string.max_select_crop));
                return;
            }
        }
        //选中
        item.isSelected = true;
        mSelectedCropAdapter.add(item);
        //当前选中作物的个数
        int itemCount = mSelectedCropAdapter.getItemCount();
        //设置选中的个数
        mTvSelectedCount.setText(String.valueOf(itemCount));
        //滑动到最后一个
        mSelectRecyclerView.scrollToPosition(itemCount - 1);
        mSearchCropAdapter.selectCrop(item.id);
        mCropAdapter.selectCrop(item.id);
    }


    @Override
    public void complete() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.action_complete) {
            if (mType == 0) {
                onSelectDone(mSelectedCropAdapter.getAllData());
            } else {
                if (mSelectedCropAdapter.getAllData().size() == 0) {
                    UiUtils.makeText("请选择分类");
                    return false;
                }
                EventBus.getDefault().post(new CropSelectEvent(mSelectedCropAdapter.getAllData()));
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSelectDone(List<Plant> allData) {
        setResult(RESULT_OK, new Intent().putExtra(REQUEST_REUSLT, (Serializable) allData));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete, menu);
        return true;
    }

    @Override
    public void showCropList(List<Crop> crops) {
        if (mFollowPlant != null && !mFollowPlant.isEmpty()) {
            for (Crop crop : crops) {
                for (Plant plant : crop.plant) {
                    for (Plant follow : mFollowPlant) {
                        if (follow.id == plant.id) {
                            plant.isSelected = true;
                            mSelectedCropAdapter.add(plant);
                        }
                    }
                }
            }
        }

        mCategoryAdapter.addAll(crops, true);
        mCategoryAdapter.setSelected(0);
    }

    @Override
    public void searchCropList(List<Plant> data) {
        List<Plant> selectData = mSelectedCropAdapter.getAllData();
        for (Plant plant : data) {
            for (Plant select : selectData) {
                if (select.id == plant.id) {
                    plant.isSelected = true;
                }
            }
        }
        mSearchCropAdapter.addAll(data, true);
    }
}