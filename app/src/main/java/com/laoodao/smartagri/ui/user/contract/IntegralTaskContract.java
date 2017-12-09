package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.IntegralTask;

import java.util.List;


/**
 * Created by 欧源 on 2017/4/26.
 */

public interface IntegralTaskContract {


    interface IntegralTaskView extends BaseView {

        void showTaskList(List<IntegralTask> taskList);
    }

    interface Presenter<T> extends BasePresenter<T> {

        void getTaskList();
    }
}