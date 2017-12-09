package com.laoodao.smartagri.di.component;

import com.laoodao.smartagri.di.scope.ActivityScope;
import com.laoodao.smartagri.ui.farmland.activity.AccountDetailActivity;
import com.laoodao.smartagri.ui.farmland.activity.AddFarmlandActivity;
import com.laoodao.smartagri.ui.farmland.activity.AddOperationActivity;
import com.laoodao.smartagri.ui.farmland.activity.ChoiceOperationActivity;
import com.laoodao.smartagri.ui.farmland.activity.FarmlandDetailActivity;
import com.laoodao.smartagri.ui.farmland.activity.FarmlandManagerActivity;

import com.laoodao.smartagri.ui.farmland.activity.IncomeRecordEditActivity;
import com.laoodao.smartagri.ui.farmland.fragment.FarmlandManagementFragment;

import com.laoodao.smartagri.ui.farmland.activity.ImagePreviewActivity;


import com.laoodao.smartagri.ui.farmland.fragment.IncomeRecordFragment;

import com.laoodao.smartagri.ui.farmland.fragment.MyFarmlandFragment;

import dagger.Component;

/**
 * Created by WORK on 2017/4/23.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface FarmlandComponent {

    FarmlandManagerActivity inject(FarmlandManagerActivity activity);

    MyFarmlandFragment inject(MyFarmlandFragment fragment);

    IncomeRecordFragment inject(IncomeRecordFragment fragment);

    AddFarmlandActivity inject(AddFarmlandActivity activity);


    FarmlandDetailActivity inject(FarmlandDetailActivity activity);

    AddOperationActivity inject(AddOperationActivity activity);

    ChoiceOperationActivity inject(ChoiceOperationActivity activity);

    FarmlandManagementFragment inject(FarmlandManagementFragment fragment);

    AccountDetailActivity inject(AccountDetailActivity activity);

    ImagePreviewActivity inject(ImagePreviewActivity activity);

    IncomeRecordEditActivity inject(IncomeRecordEditActivity activity);

}
