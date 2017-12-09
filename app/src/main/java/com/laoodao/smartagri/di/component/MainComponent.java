package com.laoodao.smartagri.di.component;

import com.laoodao.smartagri.di.scope.ActivityScope;
import com.laoodao.smartagri.ui.home.activity.MainActivity;
import com.laoodao.smartagri.ui.home.activity.WeatherActivity;
import com.laoodao.smartagri.ui.home.activity.WebViewActivity;
import com.laoodao.smartagri.ui.home.fragment.HomeFragment;

import dagger.Component;

/**
 * Created by Administrator on 2017/4/11.
 */
@ActivityScope
@Component(dependencies = AppComponent.class)
public interface MainComponent {

    MainActivity inject(MainActivity activity);

    HomeFragment inject(HomeFragment fragment);

    WeatherActivity inject(WeatherActivity activity);

    WebViewActivity inject(WebViewActivity activity);
}
