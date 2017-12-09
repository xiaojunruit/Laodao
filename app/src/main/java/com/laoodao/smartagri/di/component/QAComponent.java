package com.laoodao.smartagri.di.component;

import com.laoodao.smartagri.di.scope.ActivityScope;
import com.laoodao.smartagri.ui.qa.activity.AskActivity;
import com.laoodao.smartagri.ui.qa.activity.CropActivity;
import com.laoodao.smartagri.ui.qa.activity.QuestionDetailActivity;
import com.laoodao.smartagri.ui.qa.activity.QuestionSearchActivity;
import com.laoodao.smartagri.ui.qa.fragment.FollowContentFragment;
import com.laoodao.smartagri.ui.qa.fragment.MyAnswerFragment;
import com.laoodao.smartagri.ui.qa.fragment.MyAskFragment;
import com.laoodao.smartagri.ui.qa.fragment.MyQATabLayoutFragment;
import com.laoodao.smartagri.ui.qa.fragment.MyWonderUsersFragment;
import com.laoodao.smartagri.ui.qa.fragment.NewestQuestionFragment;
import com.laoodao.smartagri.ui.qa.fragment.QAFragment;
import com.laoodao.smartagri.ui.qa.fragment.WonderAnswerFragment;

import dagger.Component;

/**
 * Created by WORK on 2017/4/15.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface QAComponent {
    QAFragment inject(QAFragment fragment);

    NewestQuestionFragment inject(NewestQuestionFragment fragment);

    FollowContentFragment inject(FollowContentFragment fragment);

    QuestionDetailActivity inject(QuestionDetailActivity activity);

    AskActivity inject(AskActivity activity);

    MyQATabLayoutFragment inject(MyQATabLayoutFragment fragment);

    MyAskFragment inject(MyAskFragment fragment);

    WonderAnswerFragment inject(WonderAnswerFragment fragment);

    MyAnswerFragment inject(MyAnswerFragment fragment);

    CropActivity inject(CropActivity activity);

    QuestionSearchActivity inject(QuestionSearchActivity activity);

    MyWonderUsersFragment inject(MyWonderUsersFragment fragment);

}
