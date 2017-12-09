package com.laoodao.smartagri.ui.discovery.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseFragment;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.CateList;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.CategoryEvent;
import com.laoodao.smartagri.ui.discovery.adapter.CategoryAdapter;
import com.laoodao.smartagri.ui.discovery.contract.CategoryContract;
import com.laoodao.smartagri.ui.discovery.presenter.CategoryPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by WORK on 2017/4/18.
 */

public class CategoryFragment extends BaseFragment<CategoryPresenter> implements CategoryContract.CategoryView {


    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerview;
    private CateList mCate;
    private int mNum;
    private CategoryAdapter mAdapter;

    @Override
    public void complete() {
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_category;
    }

    public void dataChange() {
        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();

    }


    public static CategoryFragment newInstance(int num, CateList cate) {

        Bundle args = new Bundle();
        args.putSerializable("cate", cate);
        args.putInt("num", num);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    public void configViews() {
        mAdapter = new CategoryAdapter(getContext());
        ArrayList<CateList.CategoryListBean> list = new ArrayList<>();
        mCate = (CateList) getArguments().getSerializable("cate");
        mNum = getArguments().getInt("num");
        if ((mNum + 1) * 4 > mCate.categoryList.size()) {
            for (int i = mNum * 4; i < mCate.categoryList.size(); i++) {
                list.add(mCate.categoryList.get(i));
            }

        } else {
            for (int i = mNum * 4; i < (mNum + 1) * 4; i++) {
                list.add(mCate.categoryList.get(i));
            }
        }
        mRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        mAdapter.addAll(list);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(position -> {
            for (int i = 0; i < mCate.categoryList.size(); i++) {
                mCate.categoryList.get(i).isSelect = false;
            }
            mAdapter.getAllData().get(position).isSelect = true;
            CategoryEvent event = new CategoryEvent();
            event.id = mAdapter.getAllData().get(position).id;
            EventBus.getDefault().post(event);
        });
    }



}