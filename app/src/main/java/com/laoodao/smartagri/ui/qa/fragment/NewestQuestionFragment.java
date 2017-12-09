package com.laoodao.smartagri.ui.qa.fragment;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.ejz.xrecyclerview.XRecyclerView;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseXRVFragment;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.FollowQuestionEvent;
import com.laoodao.smartagri.event.ReleaseQuestionEvent;
import com.laoodao.smartagri.event.SelectAreaChangeEvent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.event.ShareIdEvent;
import com.laoodao.smartagri.event.WonderEvent;
import com.laoodao.smartagri.ui.qa.adapter.NewestQuestionAdapter;
import com.laoodao.smartagri.ui.qa.contract.NewestQuestionContract;
import com.laoodao.smartagri.ui.qa.dialog.QuestionSuccessDialog;
import com.laoodao.smartagri.ui.qa.presenter.NewestQuestionPresenter;
import com.laoodao.smartagri.utils.LogUtil;
import com.laoodao.smartagri.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WORK on 2017/4/15.newAsk
 * <p>
 * 问答模块->最新问题列表
 */

public class NewestQuestionFragment extends BaseXRVFragment<NewestQuestionPresenter> implements NewestQuestionContract.NewestQuestionView {
    private int QuestionId;
    private Question item;

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getNewsQuestionList(page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getNewsQuestionList(page);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_qa_newest;
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
        mAdapter = new NewestQuestionAdapter(mActivity, 0);
        initAdapter();
        XRecyclerView.DividerItemDecoration decoration = mRecyclerView.new DividerItemDecoration(ContextCompat.getColor(mActivity, R.color.transparent), UiUtils.dip2px(10));
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setHasFixedSize(true);
    }


//    @Subscribe
//    public void ChangeWonderEvent(WonderEvent event) {
//        if (event.position != -1 && event.type == 0) {
//            Question question = (Question) mAdapter.getItem(event.position);
//            question.myWonder = event.isFollow;
//            mAdapter.notifyDataSetChanged();
//        }
//    }


    @Override
    protected void lazyFetchData() {
        onRefresh();
    }


    @Override
    public void showNewsQuestionList(List<Question> questionList, boolean isRefresh) {
        mAdapter.addAll(questionList, isRefresh);
    }


    @Override
    public void followSuccess(int position, Map<String, String> data) {
        Question item = ((Question) mAdapter.getItem(position));

        item.wonderMember = data.get("member_names");
        item.wonderNum = Integer.parseInt(data.get("member_names_total"));
        item.myWonder = item.isFollow() ? 0 : 1;
        mAdapter.notifyDataSetChanged();
        EventBus.getDefault().post(new WonderEvent());
    }

    @Override
    public void shareSuccess() {

    }

    /**
     * 切换城市刷新列表数据
     *
     * @return
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectAreaChange(SelectAreaChangeEvent event) {
        onRefresh();
    }

    @Override
    protected boolean enableEventBus() {
        return true;
    }

    /**
     * 关注问题事件
     *
     * @param event
     */
    @Subscribe
    public void onFollowQuestionReceive(FollowQuestionEvent event) {
        if (event.type == 0) {
            if (event.isFollow) {
                mPresenter.unfollow(event.questionId, event.position);
            } else {
                mPresenter.follow(event.questionId, event.position);
            }
        }

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


//    @Subscribe
//    public void shareQuestion(ShareIdEvent event) {
//        if (getUserVisibleHint())
//            QuestionId = event.id;
//    }

    private boolean layoutVisible;

//    @Subscribe
//    public void shareSuccessEvent(ShareEvent event) {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (layoutVisible && getUserVisibleHint()) {
//                    mPresenter.share("ask", QuestionId);
//                }
//            }
//        }, 500);
//    }


    @Override
    public void onResume() {
        super.onResume();
        layoutVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        layoutVisible = false;
    }
}
