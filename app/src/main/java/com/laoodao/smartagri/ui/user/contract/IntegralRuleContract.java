package com.laoodao.smartagri.ui.user.contract;

import com.laoodao.smartagri.base.BasePresenter;
import com.laoodao.smartagri.base.BaseView;
import com.laoodao.smartagri.bean.IntegralRule;

import java.util.List;

/**
 * Created by WORK on 2017/5/10.
 */

public interface IntegralRuleContract {
    interface IntegralRuleView extends BaseView{
        void integralRule(List<IntegralRule> list);
    }
    interface Presenter<T> extends BasePresenter<T>{
        void requestIntegralRule();
    }
}
