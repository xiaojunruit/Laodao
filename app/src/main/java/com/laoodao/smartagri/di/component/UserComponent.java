package com.laoodao.smartagri.di.component;

import com.laoodao.smartagri.di.scope.ActivityScope;
import com.laoodao.smartagri.ui.user.activity.ApplyCertificationActivity;
import com.laoodao.smartagri.ui.user.activity.ChatActivity;
import com.laoodao.smartagri.ui.user.activity.ContactsListActivity;
import com.laoodao.smartagri.ui.user.activity.FansActivity;
import com.laoodao.smartagri.ui.user.activity.FollowFarmShopActivity;
import com.laoodao.smartagri.ui.user.activity.IntegralRuleActivity;
import com.laoodao.smartagri.ui.user.activity.IntegralTaskActivity;
import com.laoodao.smartagri.ui.qa.activity.ImgViewPagerActivity;
import com.laoodao.smartagri.ui.user.activity.BindLoginActivity;
import com.laoodao.smartagri.ui.user.activity.BoundAccountActivity;
import com.laoodao.smartagri.ui.user.activity.FarmingCapitalActivity;
import com.laoodao.smartagri.ui.user.activity.FastRegisterActivity;
import com.laoodao.smartagri.ui.user.activity.ForgetPwdActivity;
import com.laoodao.smartagri.ui.user.activity.HistoryRecordActivity;
import com.laoodao.smartagri.ui.user.activity.IdeaFeedbackActivity;
import com.laoodao.smartagri.ui.user.activity.IntegralDetailActivity;
import com.laoodao.smartagri.ui.user.activity.IntroductionActivity;
import com.laoodao.smartagri.ui.user.activity.InviteActivity;
import com.laoodao.smartagri.ui.user.activity.InviteListActivity;
import com.laoodao.smartagri.ui.user.activity.JointLoginActivity;
import com.laoodao.smartagri.ui.user.activity.LoanRecordActivity;
import com.laoodao.smartagri.ui.user.activity.LoginActivity;
import com.laoodao.smartagri.ui.farmland.activity.RecordActivity;
import com.laoodao.smartagri.ui.user.activity.MessageDetailActivity;
import com.laoodao.smartagri.ui.user.activity.MsgActivity;
import com.laoodao.smartagri.ui.user.activity.MyCollectionActivity;
import com.laoodao.smartagri.ui.user.activity.MyLoanActivity;
import com.laoodao.smartagri.ui.user.activity.OrderDetailActivity;
import com.laoodao.smartagri.ui.user.activity.PaymentDetailActivity;
import com.laoodao.smartagri.ui.user.activity.RepaymentOrderActivity;
import com.laoodao.smartagri.ui.user.activity.ReturnGoodsDetailActivity;
import com.laoodao.smartagri.ui.user.activity.TradeRecordDetailActivity;
import com.laoodao.smartagri.ui.user.activity.RegisterActivity;
import com.laoodao.smartagri.ui.user.activity.SettingActivity;
import com.laoodao.smartagri.ui.user.activity.SignInActivity;
import com.laoodao.smartagri.ui.user.activity.UpdateNickNameActivity;
import com.laoodao.smartagri.ui.user.activity.UpdatePhoneActivity;
import com.laoodao.smartagri.ui.user.activity.UpdatePwdActivity;
import com.laoodao.smartagri.ui.user.activity.UpdateTrueNameActivity;
import com.laoodao.smartagri.ui.user.activity.UserHomePageActivity;
import com.laoodao.smartagri.ui.user.activity.UserInfoActivity;

import com.laoodao.smartagri.ui.user.fragment.AskCollectionFragment;
import com.laoodao.smartagri.ui.user.fragment.SupplyCollectionFragment;
import com.laoodao.smartagri.ui.user.fragment.DynamicFragment;
import com.laoodao.smartagri.ui.user.fragment.LoanRecordListFragment;
import com.laoodao.smartagri.ui.user.fragment.MyOrderFragment;
import com.laoodao.smartagri.ui.user.fragment.MyOrderListFragment;
import com.laoodao.smartagri.ui.user.fragment.NoticeFragment;
import com.laoodao.smartagri.ui.user.fragment.PaymentHistoryFragment;
import com.laoodao.smartagri.ui.user.fragment.PendingConfirmOrderFragment;
import com.laoodao.smartagri.ui.user.fragment.ReplyFragment;
import com.laoodao.smartagri.ui.user.fragment.ReturnGoodsFragment;
import com.laoodao.smartagri.ui.user.fragment.SettingPwdFragment;
import com.laoodao.smartagri.ui.user.fragment.TradeRecordFragment;
import com.laoodao.smartagri.ui.user.fragment.UserFragmnet;
import com.laoodao.smartagri.ui.user.fragment.ValidatePhoneFragment;

import dagger.Component;

/**
 * Created by WORK on 2017/4/13.
 */


@ActivityScope
@Component(dependencies = AppComponent.class)
public interface UserComponent {
    IdeaFeedbackActivity inject(IdeaFeedbackActivity activity);

    MyLoanActivity inject(MyLoanActivity activity);

    IntegralDetailActivity inject(IntegralDetailActivity activity);

    InviteActivity inject(InviteActivity activity);

    SettingActivity inject(SettingActivity activity);

    LoanRecordActivity inject(LoanRecordActivity activity);

    InviteListActivity inject(InviteListActivity activity);

    UserInfoActivity inject(UserInfoActivity activity);

    ForgetPwdActivity inject(ForgetPwdActivity activity);

    UpdatePwdActivity inject(UpdatePwdActivity activity);

    BoundAccountActivity inject(BoundAccountActivity activity);

    RegisterActivity inject(RegisterActivity activity);

    UpdatePhoneActivity inject(UpdatePhoneActivity activity);

    UpdateNickNameActivity inject(UpdateNickNameActivity activity);

    LoginActivity inject(LoginActivity activity);

    UserFragmnet inject(UserFragmnet fragmnet);

    LoanRecordListFragment inject(LoanRecordListFragment fragment);

    TradeRecordFragment inject(TradeRecordFragment fragment);

    TradeRecordDetailActivity inject(TradeRecordDetailActivity activity);

    ImgViewPagerActivity inject(ImgViewPagerActivity activity);

    SettingPwdFragment inject(SettingPwdFragment fragment);

    ValidatePhoneFragment inject(ValidatePhoneFragment fragment);

    RecordActivity inject(RecordActivity activity);

    SignInActivity inject(SignInActivity activity);

    IntegralTaskActivity inject(IntegralTaskActivity activity);

    JointLoginActivity inject(JointLoginActivity activity);

    BindLoginActivity inject(BindLoginActivity activity);

    FarmingCapitalActivity inject(FarmingCapitalActivity activity);

    MyOrderFragment inject(MyOrderFragment inject);

    PaymentHistoryFragment inject(PaymentHistoryFragment fragment);

    OrderDetailActivity inject(OrderDetailActivity activity);

    MyOrderListFragment inject(MyOrderListFragment fragment);

    PaymentDetailActivity inject(PaymentDetailActivity activity);

    FastRegisterActivity inject(FastRegisterActivity activity);

    MsgActivity inject(MsgActivity activity);

    ReplyFragment inject(ReplyFragment fragment);

    NoticeFragment inject(NoticeFragment fragment);

    DynamicFragment inject(DynamicFragment fragment);

    PendingConfirmOrderFragment inject(PendingConfirmOrderFragment fragment);

    HistoryRecordActivity inject(HistoryRecordActivity activity);

    IntegralRuleActivity inject(IntegralRuleActivity activity);

    MessageDetailActivity inject(MessageDetailActivity activity);

    ReturnGoodsFragment inject(ReturnGoodsFragment fragment);

    ReturnGoodsDetailActivity inject(ReturnGoodsDetailActivity activity);

    RepaymentOrderActivity inject(RepaymentOrderActivity activity);

    UserHomePageActivity inject(UserHomePageActivity activity);

    FansActivity inject(FansActivity activity);

    FollowFarmShopActivity inject(FollowFarmShopActivity activity);

    ApplyCertificationActivity inject(ApplyCertificationActivity activity);

    ContactsListActivity inject(ContactsListActivity activity);

    IntroductionActivity inject(IntroductionActivity activity);

    ChatActivity inject(ChatActivity activity);

    MyCollectionActivity inject(MyCollectionActivity activity);

    AskCollectionFragment inject(AskCollectionFragment fragment);

    SupplyCollectionFragment inject(SupplyCollectionFragment fragment);

    UpdateTrueNameActivity inject(UpdateTrueNameActivity activity);


}
