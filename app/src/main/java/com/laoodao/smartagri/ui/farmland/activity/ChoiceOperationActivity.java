package com.laoodao.smartagri.ui.farmland.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ejz.imageSelector.adapter.GridSpacingItemDecoration;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseActivity;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.RecordClassification;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerFarmlandComponent;
import com.laoodao.smartagri.event.ChoiceOperationEvent;
import com.laoodao.smartagri.ui.farmland.adapter.ChoiceOperationAdapter;
import com.laoodao.smartagri.ui.farmland.contract.ChoiceOperationContract;
import com.laoodao.smartagri.ui.farmland.presenter.ChoiceOperationPresenter;
import com.laoodao.smartagri.view.recyclerview.decoration.SpaceDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * Created by WORK on 2017/4/25.
 */

public class ChoiceOperationActivity extends BaseXRVActivity<ChoiceOperationPresenter> implements ChoiceOperationContract.ChoiceOperationView {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_choice_operation;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerFarmlandComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        initAdapter(ChoiceOperationAdapter.class);
        mPresenter.requestChoiceOperation();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mAdapter.setOnItemClickListener(position -> {
            List<RecordClassification> list = mAdapter.getAllData();
            RecordClassification classification = list.get(position);
            EventBus.getDefault().post(new ChoiceOperationEvent(classification.id, classification.title));
            finish();
        });
        mRecyclerView.addItemDecoration(new SpaceDecoration(3));
    }


    @Override
    public void choiceOperationSuccess(List<RecordClassification> list) {
        mAdapter.addAll(list);
    }
}
