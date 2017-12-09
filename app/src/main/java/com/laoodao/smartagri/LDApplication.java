package com.laoodao.smartagri;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.baidu.mapapi.SDKInitializer;
import com.growingio.android.sdk.collection.Configuration;
import com.growingio.android.sdk.collection.GrowingIO;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;
import com.laoodao.smartagri.base.ActivityLifecycle;
import com.laoodao.smartagri.base.AppManager;
import com.laoodao.smartagri.di.component.AppComponent;
import com.laoodao.smartagri.di.component.DaggerAppComponent;
import com.laoodao.smartagri.di.module.AppModule;
import com.laoodao.smartagri.ui.user.activity.ChatActivity;
import com.laoodao.smartagri.utils.Packer;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.tencent.bugly.crashreport.CrashReport;

import javax.inject.Inject;

import timber.log.Timber;

//import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Administrator on 2017/4/11.
 */

public class LDApplication extends Application {


    private static LDApplication mInstance;
    private AppComponent appComponent;
    private AppModule mAppModule;
    @Inject
    protected AppManager mAppManager;
    @Inject
    protected ActivityLifecycle mActivityLifecycle;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Global.getInstance().init(mInstance);
        SDKInitializer.initialize(this);
        initCompoent();
        Timber.plant(new Timber.DebugTree());
        registerActivityLifecycleCallbacks(mActivityLifecycle);
        // bugly
        installBugly();
        FlowManager.init(this);

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        GrowingIO.startWithConfiguration(this, new Configuration()
                .useID()
                .trackAllFragments()
                .setChannel(Packer.getMarket(mInstance, BuildConfig.FLAVOR)));

        PushManager.getInstance().initialize(mInstance);
        setPushTag("app.init", getChannel(), BuildConfig.VERSION_NAME);

        //初始化环信sdk
        EMOptions options = new EMOptions();
        options.setGCMNumber(BuildConfig.GCM_NUMBER);
        EMClient.getInstance().init(this, options);
        EaseUI.getInstance().init(this, options);
        EaseAvatarOptions avatarOptions = new EaseAvatarOptions();
        avatarOptions.setAvatarShape(2);
        EaseUI.getInstance().setAvatarOptions(avatarOptions);
    }

    private void setEaseUIProviders() {
        if (EaseUI.getInstance().getNotifier() == null) {
            return;
        }
        EaseUI.getInstance().getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {

            @Override
            public String getTitle(EMMessage message) {
                //you can update title here
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                //you can update icon here
                return 0;
            }

            @Override
            public String getDisplayedText(EMMessage message) {
                // be used on notification bar, different text according the message type.
                String ticker = EaseCommonUtils.getMessageDigest(message, mInstance);
                if (message.getType() == EMMessage.Type.TXT) {
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                return message.getFrom() + ": " + ticker;
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                // here you can customize the text.
                // return fromUsersNum + "contacts send " + messageNum + "messages to you";
                return null;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                Intent intent = new Intent(mInstance, ChatActivity.class);
                intent.putExtra("userId", message.getFrom());
                intent.putExtra("chatType", 1);
                return intent;
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static void setPushTag(String sn, String... tags) {
        Tag[] items = new Tag[tags.length];
        for (int i = 0; i < tags.length; i++) {
            Tag t = new Tag();
            //name 字段只支持：中文、英文字母（大小写）、数字、除英文逗号以外的其他特殊符号, 具体请看代码示例
            t.setName(tags[i]);
            items[i] = t;
        }
        PushManager.getInstance().setTag(mInstance, items, sn);

    }

    public static String getChannel() {
        return Packer.getMarket(mInstance, BuildConfig.FLAVOR);
    }

    private void installBugly() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
        strategy.setAppChannel(Packer.getMarket(this, BuildConfig.FLAVOR));
        strategy.setAppVersion(BuildConfig.VERSION_NAME);
//        strategy.setDeviceID(DeviceUtils.getIMEI(this));
        CrashReport.initCrashReport(this, BuildConfig.BUGLY_ID, BuildConfig.DEBUG, strategy);
    }

    private void initCompoent() {
        mAppModule = new AppModule(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(mAppModule)
                .build();
        appComponent.inject(this);

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mAppModule != null)
            this.mAppModule = null;
        if (mActivityLifecycle != null) {
            unregisterActivityLifecycleCallbacks(mActivityLifecycle);
        }
        if (mAppManager != null) {//释放资源
            this.mAppManager.release();
            this.mAppManager = null;
        }
        if (mInstance != null)
            this.mInstance = null;
    }

    public static LDApplication getInstance() {
        return mInstance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static String getPushId() {
        return PushManager.getInstance().getClientid(mInstance);
    }
}
