package com.laoodao.smartagri.view.comment;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laoodao.smartagri.R;
import com.laoodao.smartagri.bean.Comment;
import com.laoodao.smartagri.utils.SpannableStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * des:评论适配器
 * Created by xsf
 * on 2016.07.11:11
 */
public class CommentAdapter {

    private Context mContext;
    private CommentListView mListview;
    private List<Comment> mDatas;

    public CommentAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<Comment>();
    }

    public CommentAdapter(Context context, List<Comment> datas) {
        mContext = context;
        setDatas(datas);
    }

    protected void bindListView(CommentListView listView) {
        if (listView == null) {
            throw new IllegalArgumentException("CommentListView is null....");
        }
        mListview = listView;
    }

    public void setDatas(List<Comment> datas) {
        if (datas == null) {
            datas = new ArrayList<Comment>();
        }

        mDatas = datas;
    }

    public List<Comment> getDatas() {
        return mDatas;
    }

    public int getCount() {
        if (mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    public Comment getItem(int position) {
        if (mDatas == null) {
            return null;
        }
        if (position < mDatas.size()) {
            return mDatas.get(position);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    private int getColor(int redId) {
        return mContext.getResources().getColor(redId);
    }

    public interface OnItemClickListener {
        public void onClick(int position, Comment comment);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    private View getView(final int position) {
        View convertView = View.inflate(mContext,
                R.layout.item_social_comment, null);
        TextView commentTv = (TextView) convertView.findViewById(R.id.commentTv);
        SpannableStringUtils.Builder builder = SpannableStringUtils.getBuilder(mContext, "");
        Comment comment = mDatas.get(position);

        if (TextUtils.isEmpty(comment.replayTo)) {
            builder.append(comment.nickname);
            builder.setForegroundColor(getColor(R.color.colorAccent));
        } else {
            builder.append(comment.nickname)
                    .setForegroundColor(getColor(R.color.colorAccent))
                    .append(mContext.getString(R.string.replay))
                    .append(comment.replayTo)
                    .setForegroundColor(getColor(R.color.colorAccent));
        }
        builder.append("：")
                .append(comment.content);

        if (commentTv != null) {
            commentTv.setText(builder.create());
            commentTv.setOnClickListener(v -> {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onClick(position, comment);
                }
            });
        }
        return convertView;
    }

    public void notifyDataSetChanged() {
        if (mListview == null) {
            throw new NullPointerException("listview is null, please bindListView first...");
        }
        mListview.removeAllViews();
        if (mDatas == null || mDatas.size() == 0) {
            mListview.setVisibility(View.GONE);
            return;
        } else {
            mListview.setVisibility(View.VISIBLE);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < mDatas.size(); i++) {
            final int index = i;
            View view = getView(index);
            if (view == null) {
                throw new NullPointerException("listview item layout is null, please check getView()...");
            }
            mListview.addView(view, index, layoutParams);
        }
    }

}
