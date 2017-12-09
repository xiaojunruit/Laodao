package com.laoodao.smartagri.ui.qa.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ejz.multistateview.MultiStateView;
import com.ejz.xrecyclerview.XRecyclerView;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Plant;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.User;
import com.laoodao.smartagri.bean.base.Result;
import com.laoodao.smartagri.common.Constant;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.CropSelectEvent;
import com.laoodao.smartagri.event.FollowQuestionEvent;
import com.laoodao.smartagri.event.ReleaseQuestionEvent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.event.ShareIdEvent;
import com.laoodao.smartagri.event.ShareIntentDataEvent;
import com.laoodao.smartagri.event.UserInfoChangedEvent;
import com.laoodao.smartagri.event.WonderEvent;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.qa.activity.CropActivity;
import com.laoodao.smartagri.ui.qa.adapter.NewestQuestionAdapter;
import com.laoodao.smartagri.ui.qa.adapter.QASelectedCropAdapter;
import com.laoodao.smartagri.ui.qa.contract.FollowContentContract;
import com.laoodao.smartagri.ui.qa.presenter.FollowContentPresenter;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.utils.ACache;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.wxapi.QQSdk;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WORK on 2017/4/17.
 */

public class FollowContentFragment extends BaseXRVFragment<FollowContentPresenter> implements FollowContentContract.FollowContentView {
    private int QuestionId;

    @BindView(R.id.no_follow)
    LinearLayout mNoFollow;
    @BindView(R.id.tv_follow)
    RoundTextView mTvFollow;
    @BindView(R.id.tv_error_mark)
    TextView mTvErrorMark;
    @BindView(R.id.select_recyclerview)
    RecyclerView mSelectRecyclerView;
    @BindView(R.id.iv_add_crop)
    ImageView mIvAddCrop;
    @BindView(R.id.follow_crop)
    LinearLayout mFollowCrop;
    private List<Plant> mSelectPlantList = new ArrayList<>();
    private QASelectedCropAdapter mQASelectedCropAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_follow_content;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

        DaggerQAComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    public void configViews() {
        mSelectRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mQASelectedCropAdapter = new QASelectedCropAdapter(getContext());
        mSelectRecyclerView.setAdapter(mQASelectedCropAdapter);
        mAdapter = new NewestQuestionAdapter(mActivity, 1);
        initAdapter();
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);
        mQASelectedCropAdapter.setOnItemClickListener(position -> {
            if (mQASelectedCropAdapter.getAllData().size() <= 1) {
                UiUtils.makeText("至少保留一种作物");
                return;
            }
            for (int i = 0; i < mQASelectedCropAdapter.getAllData().size(); i++) {
                if (i != position) {
                    mQASelectedCropAdapter.getAllData().get(i).imgIsVisibility = false;
                }
            }
            if (mQASelectedCropAdapter.getAllData().get(position).imgIsVisibility) {
                mQASelectedCropAdapter.getAllData().get(position).imgIsVisibility = false;
            } else {
                mQASelectedCropAdapter.getAllData().get(position).imgIsVisibility = true;
            }
            mQASelectedCropAdapter.notifyDataSetChanged();
        });

    }

    @Override
    protected void lazyFetchData() {
        if (!Global.getInstance().isLoggedIn()) {
            return;
        }
        onRefresh();
    }

//    @Subscribe
//    public void ChangeWonderEvent(WonderEvent event) {
//        if (event.position != -1 && event.type == 1) {
//            Question question = (Question) mAdapter.getItem(event.position);
//            question.myWonder = event.isFollow;
//            mAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    public void initData(List<Question> questionList, boolean isRefresh) {
        mAdapter.addAll(questionList, isRefresh);
    }


    @Override
    public void followSuccess(int position, Map<String, String> data) {
        Question item = (Question) mAdapter.getItem(position);
        item.wonderMember = data.get("member_names");
        item.wonderNum = Integer.parseInt(data.get("member_names_total"));
        item.myWonder = item.isFollow() ? 0 : 1;
        mAdapter.notifyDataSetChanged();
        EventBus.getDefault().post(new WonderEvent());
    }

    @Override
    public void onResume() {
        super.onResume();
        layoutVisible = true;
        if (!Global.getInstance().isLoggedIn()) {
            mTvFollow.setText("立即登录");
            mTvErrorMark.setText("您还没有登录");
            mNoFollow.setVisibility(View.VISIBLE);
            mFollowCrop.setVisibility(View.GONE);
            mMultiStateView.setVisibility(View.GONE);
        } else if (!Global.getInstance().isFollowPlant()) {
            mTvFollow.setText("+   添加关注");
            mTvErrorMark.setText("您还没有关注作物");
            mNoFollow.setVisibility(View.VISIBLE);
            mFollowCrop.setVisibility(View.GONE);
            mMultiStateView.setVisibility(View.GONE);
        } else {
            mNoFollow.setVisibility(View.GONE);
            mMultiStateView.setVisibility(View.VISIBLE);
            mFollowCrop.setVisibility(View.VISIBLE);
        }
    }

//    @Override
//    public void onContent() {
//        new Handler().postDelayed(() -> {
//            if (mMultiStateView != null) {
//                mMultiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
//            }
//        }, 500);
//    }


    /**
     * 关注作物成功
     */
    @Override
    public void followPlantSuccess() {
        User userInfo = Global.getInstance().getUserInfo();
        if (userInfo != null && userInfo.followPlant != null) {
            userInfo.followPlant = mSelectPlantList;
            Global.getInstance().setUserInfo(userInfo);
        }
        mNoFollow.setVisibility(View.GONE);
        mMultiStateView.setVisibility(View.VISIBLE);
        mFollowCrop.setVisibility(View.VISIBLE);

        String key = Constant.NEW_FOLLOW_CONTENT_ + page;
        ACache.get(getContext()).remove(key);
        onRefresh();

    }

    @Override
    public void shareSuccess() {

    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (Global.getInstance().isLoggedIn()) {
            User user = Global.getInstance().getUserInfo();
            mQASelectedCropAdapter.addAll(user.followPlant, true);
        }
        mPresenter.getFollowContent("crops", page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getFollowContent("crops", page);
    }

    /**
     * 关注问题事件
     *
     * @param event
     */
    @Subscribe
    public void onFollowQuestionReceive(FollowQuestionEvent event) {

        if (event.type == 1) {
            if (event.isFollow) {
                mPresenter.unfollow(event.questionId, event.position);
            } else {
                mPresenter.follow(event.questionId, event.position);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userChange(UserInfoChangedEvent event) {
        if (!Global.getInstance().isLoggedIn()) {
            return;
        }
        onRefresh();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    /**
     * 新发布了问题事件
     *
     * @param event
     */
    @Subscribe
    public void releaseQuestion(ReleaseQuestionEvent event) {
        QuestionId = event.askSuccess.askId;
        onRefresh();
    }


    @OnClick({R.id.tv_follow, R.id.iv_add_crop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add_crop:
            case R.id.tv_follow:
                if (!Global.getInstance().isLoggedIn()) {
                    UiUtils.startActivity(LoginActivity.class);
                } else {
                    List<Plant> followPlant = Global.getInstance().getUserInfo().followPlant;
                    CropActivity.start(mActivity, 1, followPlant);
                }
                break;
        }

    }

    /**
     * 选择作物事件
     *
     * @param event
     */
    @Subscribe
    public void receivePlant(CropSelectEvent event) {
        requestFollowPlant(event.plantList);
    }

    private void requestFollowPlant(List<Plant> plants) {
        String ids = "";
        for (Plant plant : plants) {
            ids += plant.id + ",";
        }
        mSelectPlantList.clear();
        mSelectPlantList.addAll(plants);
        mMultiStateView.setViewState(MultiStateView.VIEW_STATE_LOADING);
        String finalIds = ids;
        new Handler().postDelayed(() -> mPresenter.followPlant(finalIds), 400);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareQuestion(ShareIdEvent event) {
        if (getUserVisibleHint())
            QuestionId = event.id;
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void shareSuccessEvent(ShareEvent event) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (layoutVisible && getUserVisibleHint()) {
//                    mPresenter.share("ask", QuestionId);
//                }
//            }
//        }, 500);
//
//    }

    private boolean layoutVisible;


    @Override
    public void onPause() {
        super.onPause();
        layoutVisible = false;
    }
}
