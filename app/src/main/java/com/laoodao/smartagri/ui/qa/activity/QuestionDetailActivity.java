package com.laoodao.smartagri.ui.qa.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundTextView;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.BaseHeaderView;
import com.laoodao.smartagri.base.BaseXRVActivity;
import com.laoodao.smartagri.bean.Answer;
import com.laoodao.smartagri.bean.Comment;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.bean.ReplySuccess;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerQAComponent;
import com.laoodao.smartagri.event.AnswerChangeEvent;
import com.laoodao.smartagri.event.CommentEvent;
import com.laoodao.smartagri.event.ShareEvent;
import com.laoodao.smartagri.event.WonderEvent;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.qa.adapter.ImagePreviewActivity;
import com.laoodao.smartagri.ui.qa.adapter.QuestionDetailAdapter;
import com.laoodao.smartagri.ui.qa.contract.QuestionDetailContract;
import com.laoodao.smartagri.ui.qa.dialog.ReplyDialog;
import com.laoodao.smartagri.ui.qa.presenter.QuestionDetailPresenter;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.ui.user.activity.UserHomePageActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by WORK on 2017/4/17.
 * <p>
 * 问答模块->问题详情
 */


public class QuestionDetailActivity extends BaseXRVActivity<QuestionDetailPresenter> implements QuestionDetailContract.QuestionDetailView, View.OnClickListener {


    @BindView(R.id.rll_reply)
    RoundLinearLayout mRllReply;
    @BindView(R.id.iv_collect)
    ImageView mIvCollect;
    @BindView(R.id.iv_share)
    ImageView mIvShare;
    @BindView(R.id.tv_comment_count)
    TextView mTvCommentCount;
    private int mQuestionId;
    private Question mQuestion;
    private ReplyDialog mReplyDialog;
    private ShareDialog mShareDialog;
    private int TYPE_ANSWER = 1;
    private int type = TYPE_ANSWER;
    private int mPosition;
    private int mType;
    private int mCid;


    public static void start(Context context, int questionId, int position, int type) {
        Bundle bundle = new Bundle();
        bundle.putInt("questionId", questionId);
        bundle.putInt("position", position);
        bundle.putInt("type", type);
        UiUtils.startActivity(context, QuestionDetailActivity.class, bundle);
    }

    public static void start(Context context, int questionId) {
        start(context, questionId, -1, -1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_question_detial;
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerQAComponent.builder()
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    @Override
    protected void configViews() {
        mQuestionId = getIntent().getIntExtra("questionId", 0);
        mPosition = getIntent().getIntExtra("position", -1);
        mType = getIntent().getIntExtra("type", -1);
        mHeader = new QuestionHeaderView(this);
        mAdapter = new QuestionDetailAdapter(this, this);
        //mAdapter = new QuestionDetailAdapter(this, this);
        //回复回答弹窗
        mReplyDialog = new ReplyDialog(this);
        mShareDialog = new ShareDialog(this);
        initAdapter();
        onRefresh();
    }

    @OnClick({R.id.rll_reply, R.id.iv_collect, R.id.iv_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
            case R.id.rll_reply:
            case R.id.tv_left:
            case R.id.iv_collect:
            case R.id.fl_follow:
                boolean loggedIn = Global.getInstance().isLoggedIn();
                if (!loggedIn) {
                    UiUtils.startActivity(LoginActivity.class);
                    return;
                }
        }


        switch (view.getId()) {
            case R.id.tv_right:
                mReplyDialog.setCallBack(reply -> {
                    int position = (int) view.getTag();
                    Answer answer = (Answer) mAdapter.getItem(position);
                    mPresenter.reply(answer.id, reply, position);
                });
                mReplyDialog.show();
                break;
            case R.id.rll_reply:
                mReplyDialog.setCallBack(reply -> {
                    mPresenter.answer(TYPE_ANSWER, mQuestionId, reply);
                });
                mReplyDialog.show();
                break;

            case R.id.tv_left:
                int position = (int) view.getTag();
                Answer answer = (Answer) mAdapter.getItem(position);
                mPresenter.praise(position, answer.id);
                break;
            case R.id.iv_collect:
                mPresenter.collect(mQuestionId);
                break;
            case R.id.iv_share:
                mShareDialog.show();
                break;
            case R.id.fl_follow:
                Answer answer1 = (Answer) mAdapter.getItem((int) view.getTag());
                //answer1.isUserWonder = answer1.isUserWonder == 1 ? 0 : 1;
                mPresenter.addUserFollow(answer1.cid, (int) view.getTag());
                // mAdapter.notifyDataSetChanged();
                break;
        }

    }

    /**
     * 回复
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveComment(CommentEvent event) {
        mReplyDialog.setCallBack(reply -> {
            mPresenter.reply(event.id, reply, event.position);
        });
        mReplyDialog.show();
    }


    class QuestionHeaderView extends BaseHeaderView {

        @BindView(R.id.avatar)
        CircleImageView mAvatar;
        @BindView(R.id.tv_nick_name)
        TextView mTvNickName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_browse_count)
        TextView mTvBrowseCount;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.content)
        LinearLayout mContent;
        @BindView(R.id.tv_want_to_kown)
        TextView mTvWantToKown;
        @BindView(R.id.ll_content)
        LinearLayout mLlContent;
        @BindView(R.id.tv_reply_count)
        TextView mTvReplyCount;
        @BindView(R.id.tv_kown_total)
        TextView mTvKownTotal;
        @BindView(R.id.btn_want_to_kown)
        RoundTextView mBtnWantToKown;
        @BindView(R.id.no_reply)
        TextView mNoReply;

        @BindView(R.id.rtv_follow)
        RoundTextView mRtvFollow;
        @BindView(R.id.rtv_member_type_name)
        RoundTextView mRtvMemberTypeName;



        public QuestionHeaderView(Context context) {
            super(context);
        }

        @Override
        protected int getLayoutHeaderViewId() {
            return R.layout.header_view_qa_detail;
        }

        //        @OnClick(R.id.btn_want_to_kown)
//        public void onClick() {
//            boolean loggedIn = Global.getInstance().isLoggedIn();
//            if (!loggedIn) {
//                UiUtils.startActivity(LoginActivity.class);
//                return;
//            }
//            if (mQuestion != null && mQuestion.isFollow()) {
//                mPresenter.unfollow(mQuestionId);
//            } else {
//                mPresenter.follow(mQuestionId);
//            }
//


        @OnClick({R.id.btn_want_to_kown, R.id.no_reply, R.id.rtv_follow, R.id.avatar})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_want_to_kown:
                    boolean loggedIn = Global.getInstance().isLoggedIn();
                    if (!loggedIn) {
                        UiUtils.startActivity(LoginActivity.class);
                        return;
                    }
                    if (mQuestion != null && mQuestion.isFollow()) {
                        mPresenter.unfollow(mQuestionId);
                    } else {
                        mPresenter.follow(mQuestionId);
                    }
                    break;
                case R.id.no_reply:
                    if (!Global.getInstance().isLoggedIn()) {
                        UiUtils.startActivity(LoginActivity.class);
                        return;
                    }
                    mReplyDialog.setCallBack(reply -> {
                        mPresenter.answer(TYPE_ANSWER, mQuestionId, reply);
                    });
                    mReplyDialog.show();
                    break;
                case R.id.rtv_follow:
                    if (!Global.getInstance().isLoggedIn()) {
                        UiUtils.startActivity(LoginActivity.class);
                        return;
                    }
                    if (mCid != 0) {
                        mPresenter.addUserFollow(mCid, -1);
                    }
                    break;
                case R.id.avatar:
                    if (mCid == 0) {
                        return;
                    }
                    if (!Global.getInstance().isLoggedIn()) {
                        UiUtils.startActivity(LoginActivity.class);
                        return;
                    }
                    UserHomePageActivity.start(view.getContext(), mCid);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareDialog.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 初始化详情头部信息
     *
     * @param question
     */
    @Override
    public void showQuestionDetail(Question question) {
        mQuestion = question;
        QuestionHeaderView header = ((QuestionHeaderView) mHeader);
        header.mTvAddress.setText(question.address);
        header.mTvNickName.setText(question.nickname);
        header.mTvTime.setText(question.time);
        header.mTvContent.setText(question.content);
        header.mTvTitle.setText(question.title);
        header.mBtnWantToKown.setTag(question.isFollow());
        header.mBtnWantToKown.setText(question.isFollow() ? getString(R.string.cancel_want_to_kown) : getString(R.string.i_want_to_kown));
        header.mTvBrowseCount.setText(getString(R.string.read_count, String.valueOf(question.browse)));
        if (question.wonderNum >= 2) {
            header.mTvKownTotal.setText(getString(R.string.wonder_answer_num_more, String.valueOf(question.wonderNum)));
        } else {
            header.mTvKownTotal.setText(getString(R.string.wonder_answer_num, String.valueOf(question.wonderNum)));
        }
        header.mTvWantToKown.setText(question.wonderMember);
        Glide.with(this).load(question.avatar).diskCacheStrategy(DiskCacheStrategy.SOURCE).dontAnimate().centerCrop().into(header.mAvatar);
        mIvCollect.setSelected(question.isCollected());
        mShareDialog.setShareInfo(question.shareInfo);
        loadImageView(question.imageList, header.mContent);
        int cid = 0;
        if (Global.getInstance().isLoggedIn()) {
            cid = Integer.parseInt(Global.getInstance().getUserInfo().cid);
        }
        if (cid == question.cid) {
            header.mRtvFollow.setVisibility(View.GONE);
        } else {
            header.mRtvFollow.setVisibility(View.VISIBLE);
            if (question.isUserWonder == 0) {
                Drawable followDrawable = ContextCompat.getDrawable(this, R.mipmap.ic_add_blue);
                header.mRtvFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                header.mRtvFollow.getDelegate().setStrokeColor(ContextCompat.getColor(this, R.color.colorAccent));
                header.mRtvFollow.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                followDrawable.setBounds(0, 0, UiUtils.dip2px(10), UiUtils.dip2px(10));
                header.mRtvFollow.setCompoundDrawables(followDrawable, null, null, null);
                header.mRtvFollow.setText("关注");
                header.mRtvFollow.setTag(0);
            } else {
                header.mRtvFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(this, R.color.common_h5));
                header.mRtvFollow.getDelegate().setStrokeColor(ContextCompat.getColor(this, R.color.white));
                header.mRtvFollow.setTextColor(ContextCompat.getColor(this, R.color.white));
                header.mRtvFollow.setCompoundDrawables(null, null, null, null);
                header.mRtvFollow.setText("已关注");
                header.mRtvFollow.setTag(1);
            }


        }
        header.mRtvMemberTypeName.setText(question.memberTypeName);
        header.mRtvMemberTypeName.setVisibility(TextUtils.isEmpty(question.memberTypeName) ? View.GONE : View.VISIBLE);
        mCid = question.cid;
        // mTvCommentCount.setText(String.valueOf(question.commentTotal));
    }

    /**
     * 动态添加图片
     *
     * @param imageList
     * @param mContent
     */

    private void loadImageView(List<String> imageList, LinearLayout mContent) {

        if (imageList.size() == 0 || mContent.getChildCount() > 0) {
            return;
        }
        mContent.setVisibility(View.VISIBLE);
        mContent.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = UiUtils.dip2px(10);
        int width = UiUtils.getScreenWidth() - UiUtils.dip2px(30);
        for (int i = 0; i < imageList.size(); i++) {
            RoundedImageView view = new RoundedImageView(this);
            Glide.with(this).load(imageList.get(i)).asBitmap().diskCacheStrategy(DiskCacheStrategy.RESULT).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    Matrix matrix = new Matrix();
                    float sale = width * 1f / resource.getWidth();
                    matrix.postScale(sale, sale);
                    Bitmap bitmap = Bitmap.createBitmap(resource, 0, 0, resource.getWidth(), resource.getHeight(), matrix, true);
                    view.setImageBitmap(bitmap);
                    view.setLayoutParams(params);
                }
            });
            int finalI = i;
            view.setOnClickListener(v -> {
                ImagePreviewActivity.start(this, imageList, finalI);
            });
            mContent.addView(view);
        }
    }

    /**
     * 获取回答列表
     *
     * @param answers
     */
    @Override
    public void showAnswerList(List<Answer> answers, boolean isRefresh) {
        QuestionHeaderView header = ((QuestionHeaderView) mHeader);
        if (answers.size() == 0) {
            header.mNoReply.setVisibility(View.VISIBLE);
        } else {
            header.mNoReply.setVisibility(View.GONE);
        }
        mAdapter.addAll(answers, isRefresh);


    }

    /**
     * 关注问题改变
     *
     * @param data
     */
    @Override
    public void followChange(Map<String, String> data) {
        WonderEvent event = new WonderEvent();
        event.isFollow = mQuestion.isFollow() ? 0 : 1;
        mQuestion.myWonder = mQuestion.isFollow() ? 0 : 1;
        mQuestion.wonderMember = data.get("member_names");
        mQuestion.wonderNum = Integer.parseInt(data.get("member_names_total"));
        showQuestionDetail(mQuestion);
        event.position = mPosition;
        event.type = mType;
        EventBus.getDefault().post(event);
    }

    /**
     * 回复成功
     *
     * @param data
     * @param position
     */
    @Override
    public void replySuccess(ReplySuccess data, int position) {
        Answer answer = (Answer) mAdapter.getItem(position);
        answer.commentCount = data.commentNum;
        Comment comment = new Comment();
        comment.id = data.id;
        comment.replayTo = data.replayTo;
        comment.nickname = data.nickname;
        comment.content = data.content;
        answer.commentList.add(comment);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 点赞成功
     *
     * @param position
     * @param data
     */
    @Override
    public void praiseSuccess(int position, Map<String, String> data) {
        Answer answer = (Answer) mAdapter.getItem(position);
        answer.isPraise = !answer.isPraise;
        if (TextUtils.isEmpty(data.get("member_names_total"))) {
            answer.praiseNum = 0;
        } else {
            answer.praiseNum = Integer.parseInt(data.get("member_names_total"));
        }
        answer.praiseName = data.get("member_names");
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 回答成功
     *
     * @param data
     */
    @Override
    public void answerSuccess(Answer data) {
        mAdapter.insert(data, 0);
        QuestionHeaderView header = ((QuestionHeaderView) mHeader);
        header.mNoReply.setVisibility(View.GONE);
        EventBus.getDefault().post(new AnswerChangeEvent());
    }

    /**
     * 收藏成功
     */
    @Override
    public void collectSuccess() {
        mIvCollect.setSelected(!mIvCollect.isSelected());
    }

    @Override
    public void shareSuccess() {

    }

    //关注用户成功
    @Override
    public void UserFollowSuccess(int position) {
        if (position == -1) {
            QuestionHeaderView header = ((QuestionHeaderView) mHeader);
            if (header.mRtvFollow.getTag() == null) return;
            int isFollow = (int) header.mRtvFollow.getTag();
            if (1 == isFollow) {
                Drawable followDrawable = ContextCompat.getDrawable(this, R.mipmap.ic_add_blue);
                header.mRtvFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                header.mRtvFollow.getDelegate().setStrokeColor(ContextCompat.getColor(this, R.color.colorAccent));
                header.mRtvFollow.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                followDrawable.setBounds(0, 0, UiUtils.dip2px(10), UiUtils.dip2px(10));
                header.mRtvFollow.setCompoundDrawables(followDrawable, null, null, null);
                header.mRtvFollow.setText("关注");
                header.mRtvFollow.setTag(0);
            } else {
                header.mRtvFollow.getDelegate().setBackgroundColor(ContextCompat.getColor(this, R.color.common_h5));
                header.mRtvFollow.getDelegate().setStrokeColor(ContextCompat.getColor(this, R.color.white));
                header.mRtvFollow.setTextColor(ContextCompat.getColor(this, R.color.white));
                header.mRtvFollow.setCompoundDrawables(null, null, null, null);
                header.mRtvFollow.setText("已关注");
                header.mRtvFollow.setTag(1);
            }
        } else {
            Answer answer1 = (Answer) mAdapter.getItem(position);
            answer1.isUserWonder = answer1.isUserWonder == 1 ? 0 : 1;
            mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareSuccessEvent(ShareEvent event) {
        mPresenter.share("ask", mQuestionId);
    }


    @Override
    protected boolean enableEventBus() {
        return true;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPresenter.getQuestionDetail(mQuestionId);
        mPresenter.getAnswerList(mQuestionId, page);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        mPresenter.getAnswerList(mQuestionId, page);
    }
}
