package com.laoodao.smartagri.ui.discovery.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.bean.BaikeAllMenu;
import com.laoodao.smartagri.bean.BaikeMenu;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerDiscoveryComponent;
import com.laoodao.smartagri.event.BaikeMenuChangeEvent;
import com.laoodao.smartagri.ui.discovery.adapter.FindAllCropAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.FindCropAdapter;
import com.laoodao.smartagri.ui.discovery.adapter.NewsMenuTouchHelperCallback;
import com.laoodao.smartagri.ui.discovery.contract.FindCropContract;
import com.laoodao.smartagri.ui.discovery.presenter.FindCropPresenter;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

public class FindCropActivity extends BaseActivity<FindCropPresenter> implements FindCropContract.FindCropView {


    @BindView(R.id.rcy_selected)
    RecyclerView mRcySelected;
    @BindView(R.id.ll_menu_name)
    LinearLayout mLlMenuName;
    @BindView(R.id.rcy_all)
    RecyclerView mRcyAll;
    @BindView(R.id.multiStateView)
    MultiStateView mMultiStateView;
    private FindCropAdapter mAdapter;
    private FindAllCropAdapter mAllAdapter;
    private ItemTouchHelper mItemTouchHelper;


    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerDiscoveryComponent
                .builder()
                .appComponent(appComponent)
                .build()
                .inject(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_find_crop;
    }

    private BaikeAllMenu baikeAllMenu;

    @Override
    protected void configViews() {
        mPresenter.requestSelectCrop();
        // mPresenter.requestAllCrop();
        mAdapter = new FindCropAdapter(this);
        mAllAdapter = new FindAllCropAdapter(this);
        mRcySelected.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });
        mRcyAll.setLayoutManager(new GridLayoutManager(this, 4) {
            @Override
            public boolean canScrollHorizontally() {
                return true;
            }

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        ItemTouchHelper.Callback callback = new NewsMenuTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRcySelected);
        mRcySelected.setAdapter(mAdapter);
        mRcyAll.setAdapter(mAllAdapter);
        mAllAdapter.setOnItemClickListener(position -> {
            BaikeMenu.ItemsBean baikeMenu = new BaikeMenu.ItemsBean();
            baikeMenu.id = mAllAdapter.getAllData().get(position).id;
            baikeMenu.name = mAllAdapter.getAllData().get(position).name;
            if (baikeAllMenu.itemsAll.size() != 0) {
                for (int i = 0; i < baikeAllMenu.itemsAll.size(); i++) {
                    for (int k = baikeAllMenu.itemsAll.get(i).items.size() - 1; k >= 0; k--) {
                        if (baikeAllMenu.itemsAll.get(i).items.get(k).id.equals(mAllAdapter.getAllData().get(position).id)) {
                            baikeAllMenu.itemsAll.get(i).items.remove(k);
                            baikeMenu.parentId = baikeAllMenu.itemsAll.get(i).id;
                        }
                    }
                }
            }
            mAdapter.add(baikeMenu);
            mAllAdapter.remove(position);
            mAllAdapter.notifyDataSetChanged();
            mAdapter.notifyDataSetChanged();
        });
        mAdapter.setOnItemClickListener(position -> {
            BaikeAllMenu.ItemsBeanAll.ItemsBean baikeMenu = new BaikeAllMenu.ItemsBeanAll.ItemsBean();

            if (mAdapter.getAllData().size() == 1) {
                UiUtils.makeText("至少保留一种作物");
                return;
            }
            baikeMenu.id = mAdapter.getAllData().get(position).id;
            baikeMenu.name = mAdapter.getAllData().get(position).name;
            String tag = "";
            for (int i = 0; i < mLlMenuName.getChildCount(); i++) {
                if (mAdapter.getAllData().get(position).parentId.equals(mLlMenuName.getChildAt(i).getTag())) {
                    mLlMenuName.getChildAt(i).setSelected(true);
                    tag = mLlMenuName.getChildAt(i).getTag().toString();
                } else {
                    mLlMenuName.getChildAt(i).setSelected(false);
                }
            }
            for (int i = 0; i < baikeAllMenu.itemsAll.size(); i++) {
                if (baikeAllMenu.itemsAll.get(i).id.equals(mAdapter.getAllData().get(position).parentId)) {
                    baikeAllMenu.itemsAll.get(i).items.add(baikeMenu);

                }
            }
            for (int k = 0; k < baikeAllMenu.itemsAll.size(); k++) {
                if (baikeAllMenu.itemsAll.get(k).id == tag) {
                    mAllAdapter.clear();
                    mAllAdapter.addAll(baikeAllMenu.itemsAll.get(k).items);
                }
            }
            mAdapter.remove(position);
            mAdapter.notifyDataSetChanged();

        });

    }


    @Override
    public void complete() {

    }


    public void addMenu(BaikeAllMenu menu) {
        if (menu == null) {
            return;
        }
        for (int i = 0; i < menu.itemsAll.size(); i++) {
            TextView textView = new TextView(this);
            View view = new View(this);
            if (i == 0) {
                textView.setSelected(true);
                mAllAdapter.addAll(menu.itemsAll.get(0).items);
            }
            textView.setText(menu.itemsAll.get(i).catename);
            textView.setTag(menu.itemsAll.get(i).id);
            textView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ContextCompat.getColor(this, R.color.common_h1));

            mLlMenuName.addView(textView);
            if (i == 0) {
                textView.setBackgroundResource(R.drawable.select_bg_blue_radiu_left2dp);
            } else if (i == menu.itemsAll.size() - 1) {
                textView.setBackgroundResource(R.drawable.select_bg_blue_radiu_right2dp);
            } else {
                textView.setBackgroundResource(R.drawable.select_bg_blue);
            }
            if (i != menu.itemsAll.size() - 1) {
                view.setLayoutParams(new LinearLayout.LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT, 0));
                view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
                mLlMenuName.addView(view);
            }
        }
        for (int i = 0; i < mLlMenuName.getChildCount(); i++) {
            mLlMenuName.getChildAt(i).setOnClickListener(v -> {
                for (int j = 0; j < mLlMenuName.getChildCount(); j++) {
                    mLlMenuName.getChildAt(j).setSelected(false);
                }
                v.setSelected(true);
                for (int k = 0; k < baikeAllMenu.itemsAll.size(); k++) {
                    if (baikeAllMenu.itemsAll.get(k).id == v.getTag()) {
                        mAllAdapter.clear();
                        mAllAdapter.addAll(baikeAllMenu.itemsAll.get(k).items);
                    }
                }
            });

        }

    }


    @Override
    public void showSelectCrop(BaikeMenu menu) {
        mAdapter.addAll(menu.items);
    }

    @Override
    public void shouAllCrop(BaikeAllMenu allMenu) {
        baikeAllMenu = allMenu;
        addMenu(allMenu);
    }

    @Override
    public void addCrop() {
        finish();
        EventBus.getDefault().post(new BaikeMenuChangeEvent());
    }

    public String ids;

    public void addMenu() {
        ids = "";
        for (BaikeMenu.ItemsBean bean : mAdapter.getAllData()) {
            ids += bean.id + ",";
        }
        mPresenter.requestAddCrop(ids);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.complete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_complete) {
            addMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void noMore(boolean noMore) {

    }

    @Override
    public void onError() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        }
    }

    @Override
    public void onEmpty() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onContent() {
        if (mMultiStateView != null) {
            mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    @Override
    public <Item> void setResult(List<Item> items, boolean isRefresh) {

    }


}