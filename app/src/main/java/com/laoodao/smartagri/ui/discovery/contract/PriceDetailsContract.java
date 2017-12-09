package com.laoodao.smartagri.ui.discovery.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.base.ListBaseView;
import com.laoodao.smartagri.bean.CottonfieldComment;
import com.laoodao.smartagri.bean.CottonfieldDetail;

import java.util.List;


/**
 * Created by Administrator on 2017/7/31 0031.
 */

public interface PriceDetailsContract {


    interface PriceDetailsView extends ListBaseView {
        void successList(CottonfieldDetail detail);

        void successCommentList(List<CottonfieldComment> comments, boolean isRefresh);

        void successAddComment(CottonfieldComment comment);

        void successFollow();

    }

    interface Presenter<T> extends BasePresenter<T> {
        void requestList(int id);

        void CommentList(int id, int page);

        void addComment(int id, String content);

        void requestFollow(int id);

        void shareBack(String tag, int id);
    }
}