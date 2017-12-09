package com.laoodao.smartagri.ui.discovery.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.MechanicalType;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.ui.discovery.adapter.SelectNewsMenuAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.NewsMenuTouchHelperCallback;
import com.laoodao.smartagri.ui.discovery.contract.SelectNewsMenuContract;
import com.laoodao.smartagri.ui.discovery.presenter.SelectNewsMenuPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WORK on 2017/5/18.
 */

public class SelectNewsMenuActivity extends BaseActivity<SelectNewsMenuPresenter> implements SelectNewsMenuContract.SelectNewsMenuView {
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private SelectNewsMenuAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private String ids = "";

    @Override
    public void complete() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_news_menu;
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
        mAdapter = new SelectNewsMenuAdapter(this);
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setAdapter(mAdapter);
        mPresenter.requestNewsMenu();
        ItemTouchHelper.Callback callback = new NewsMenuTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerview);
    }

    @Override
    public void newsMenuSuccess(List<MechanicalType> mechanicalTypeList) {
        mAdapter.addAll(mechanicalTypeList);
    }

    @Override
    public void newsMenuIdsSuucess() {
        setResult(RESULT_OK);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_complete) {
            initIds();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initIds() {
        ids = "";
        for (MechanicalType mechanicalType : mAdapter.getAllData()) {
            ids += mechanicalType.id + ",";
        }
        mPresenter.requestNewsMenuIds(ids);
    }


}
