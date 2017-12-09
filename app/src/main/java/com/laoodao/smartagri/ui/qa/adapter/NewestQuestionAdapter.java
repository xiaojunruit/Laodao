package com.laoodao.smartagri.ui.qa.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.laoodao.smartagri.Global;
import com.laoodao.smartagri.R;
import com.laoodao.smartagri.base.adapter.BaseAdapter;
import com.laoodao.smartagri.bean.Question;
import com.laoodao.smartagri.event.FollowQuestionEvent;
import com.laoodao.smartagri.event.ShareIdEvent;
import com.laoodao.smartagri.ui.market.dialog.ShareDialog;
import com.laoodao.smartagri.ui.qa.activity.QuestionDetailActivity;
import com.laoodao.smartagri.ui.qa.dialog.MenuPopup;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.ui.user.activity.UserHomePageActivity;
import com.laoodao.smartagri.utils.UiUtils;
import com.laoodao.smartagri.view.ninegridimage.NineGridImageView;
import com.laoodao.smartagri.view.ninegridimage.NineGridImageViewAdapter;
import com.laoodao.smartagri.view.recyclerview.adapter.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by WORK on 2017/4/15.
 */

public class NewestQuestionAdapter extends BaseAdapter<Question> {


    private int mType;
    private int mCid;


    public NewestQuestionAdapter(Context context, int type) {
        super(context);
        this.mType = type;
    }

    public NewestQuestionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NesetQuestionHolder(parent, R.layout.item_ask);
    }

    class NesetQuestionHolder extends BaseViewHolder<Question> implements View.OnClickListener {

        @BindView(R.id.avatar)
        CircleImageView mAvatar;
        @BindView(R.id.tv_address)
        TextView mTvAddress;
        @BindView(R.id.tv_answer)
        TextView mTvAnswer;
        @BindView(R.id.tv_browse_num)
        TextView mTvBrowseNum;
        @BindView(R.id.iv_more)
        ImageView mIvMore;
        @BindView(R.id.tv_name)
        TextView mTvName;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_wonder_answer_names)
        TextView mTvWonderAnswerNames;
        @BindView(R.id.tv_wonder_answer_num)
        TextView mTvWonderAnswerNum;
        @BindView(R.id.ll_want_to_konw)
        LinearLayout mWantToKonw;
        @BindView(R.id.grid_images)
        NineGridImageView mGridImages;

        @BindView(R.id.iv_share)
        ImageView mIvShare;
        @BindView(R.id.tv_follow)
        TextView mTvFollow;

        private MenuPopup mMenuPopup;
        private ShareDialog mShareDialog;
        private int id;
        private Drawable drawable;


        public NesetQuestionHolder(ViewGroup parent, @LayoutRes int viewType) {
            super(parent, viewType);
            drawable = ContextCompat.getDrawable(getContext(), R.mipmap.ic_preview);
            drawable.setBounds(0, 0, UiUtils.dip2px(16), UiUtils.dip2px(16));
            mGridImages.setAdapter(mAdapter);
        }


        @Override
        public void setData(Question data) {
            mTvBrowseNum.setCompoundDrawables(drawable, null, null, null);
            mTvContent.setText(data.showContent);
            //mTvTitle.setText(data.showTitle);
            mTvContent.setVisibility(TextUtils.isEmpty(data.showContent) ? View.GONE : View.VISIBLE);
            //mTvTitle.setVisibility(TextUtils.isEmpty(data.showTitle) ? View.GONE : View.VISIBLE);
            mTvTime.setText(data.time);
            mTvAddress.setText(data.address);
            mTvName.setText(data.nickname);
            mTvWonderAnswerNames.setText(data.wonderMember);

            mTvFollow.setSelected(data.isFollow());
            if (data.wonderNum >= 2) {
                mTvWonderAnswerNum.setText(UiUtils.getString(R.string.wonder_answer_num_more, data.wonderNum));
            } else {
                mTvWonderAnswerNum.setText(UiUtils.getString(R.string.wonder_answer_num, data.wonderNum));
            }
            mTvAnswer.setText(UiUtils.getString(R.string.answer_count, data.answer));
            mTvBrowseNum.setText(UiUtils.getString(R.string.browse_count, data.browse));
            UiUtils.loadImage(mAvatar, data.avatar);


            if (data.imageList.size() == 0) {
                mGridImages.setVisibility(View.GONE);
            } else {
                mGridImages.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(data.wonderMember)) {
                mWantToKonw.setVisibility(View.GONE);
            } else {
                mWantToKonw.setVisibility(View.VISIBLE);
            }
            itemView.setOnClickListener(v -> {
                QuestionDetailActivity.start(getContext(), data.id, getCurrentPosition(), mType);
            });
            mGridImages.setImagesData(data.thumbImageList);
        }


        private NineGridImageViewAdapter<String> mAdapter = new NineGridImageViewAdapter<String>() {
            @Override
            public void onDisplayImage(Context context, ImageView imageView, String url) {
                int size = getItem(getCurrentPosition()).thumbImageList.size();
                if (size > 1) {

                }
                Glide.with(getContext().getApplicationContext())
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .dontAnimate()
                        .centerCrop()
                        .dontTransform()
                        .into(imageView);
            }

            @Override
            public void onItemImageClick(Context context, int index, List<String> list) {
                Question item = getItem(getCurrentPosition());
                ImagePreviewActivity.start(getContext(), item.imageList, index);
            }
        };

        @OnClick({R.id.avatar, R.id.tv_answer, R.id.tv_browse_num, R.id.iv_more, R.id.iv_share, R.id.tv_follow})
        public void onClick(View view) {
            Question question = getItem(getCurrentPosition());
            switch (view.getId()) {
                case R.id.avatar:
                    if (getItem(getCurrentPosition()).cid == 0) {
                        return;
                    }
                    if (!Global.getInstance().isLoggedIn()) {
                        UiUtils.startActivity(LoginActivity.class);
                        return;
                    }
                    UserHomePageActivity.start(getContext(), getItem(getCurrentPosition()).cid);
                    break;
                case R.id.tv_answer:
                    break;
                case R.id.tv_browse_num:
                    break;
                case R.id.iv_more:
                    if (mMenuPopup == null) {
                        mMenuPopup = new MenuPopup(getContext(), this);
                    }
                    mMenuPopup.anchorView(mIvMore);
                    mMenuPopup.setLeftText(question.isFollow() ? UiUtils.getString(R.string.unfollow) : UiUtils.getString(R.string.follow_question));
                    mMenuPopup.show();
                    break;
                case R.id.tv_left:
//                    boolean loggedIn = Global.getInstance().isLoggedIn();
//                    if (!loggedIn) {
//                        UiUtils.startActivity(LoginActivity.class);
//                        return;
//                    }
//                    FollowQuestionEvent event = new FollowQuestionEvent();
//                    event.isFollow = question.isFollow();
//                    event.position = getCurrentPosition();
//                    event.questionId = question.id;
//                    event.type = mType;
//                    EventBus.getDefault().post(event);

                    break;
                case R.id.tv_center:
                    QuestionDetailActivity.start(getContext(), question.id, getCurrentPosition(), mType);
                    break;
                case R.id.tv_right:
//                    if (mShareDialog == null) {
//                        mShareDialog = new ShareDialog(getContext());
//                    }
//                    mShareDialog.setShareInfo(question.shareInfo);
//                    ShareIdEvent shareIdEvent = new ShareIdEvent();
//                    shareIdEvent.id = question.id;
//                    shareIdEvent.shareDialog = mShareDialog;
//                    EventBus.getDefault().post(shareIdEvent);
//                    mShareDialog.show();
                    break;
                case R.id.iv_share:
                    if (mShareDialog == null) {
                        mShareDialog = new ShareDialog(getContext());
                    }
                    mShareDialog.setShareInfo(question.shareInfo);
                    ShareIdEvent shareIdEvent = new ShareIdEvent();
                    shareIdEvent.id = question.id;
                    shareIdEvent.shareDialog = mShareDialog;
                    EventBus.getDefault().post(shareIdEvent);
                    mShareDialog.show();
                    break;
                case R.id.tv_follow:
                    boolean loggedIn = Global.getInstance().isLoggedIn();
                    if (!loggedIn) {
                        UiUtils.startActivity(LoginActivity.class);
                        return;
                    }
                    FollowQuestionEvent event = new FollowQuestionEvent();
                    event.isFollow = question.isFollow();
                    event.position = getCurrentPosition();
                    event.questionId = question.id;
                    event.type = mType;
                    EventBus.getDefault().post(event);
                    break;
            }
        }

    }

}
