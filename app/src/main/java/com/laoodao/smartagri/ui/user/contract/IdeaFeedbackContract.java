package com.laoodao.smartagri.ui.user.contract;

import android.app.Activity;
import android.content.Context;

import com.ejz.imageSelector.bean.LocalMedia;
import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.Suggestion;

import java.util.List;

/**
 * Created by WORK on 2017/4/13.
 * 意见反馈Contact
 */

public interface IdeaFeedbackContract {

    interface IdeaFeedbackView extends BaseView {
        void showSuggestionList(List<Suggestion> suggestionList);

        void addSuggestionSuccess();

    }

    interface Presenter<T> extends BasePresenter<T> {
        void getSuggestionList();

        void commitSuggestion(Activity activity, int type, String suggestion, String phone, List<LocalMedia> mSelectedImageList);

    }

}
