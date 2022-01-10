package com.surcumference.fingerprint.plugin.magisk;

import android.app.Activity;
import android.app.Application;
import androidx.annotation.Keep;

import com.surcumference.fingerprint.BuildConfig;
import com.surcumference.fingerprint.bean.PluginTarget;
import com.surcumference.fingerprint.bean.PluginType;
import com.surcumference.fingerprint.network.updateCheck.UpdateFactory;
import com.surcumference.fingerprint.plugin.PluginApp;
import com.surcumference.fingerprint.plugin.WeChatBasePlugin;
import com.surcumference.fingerprint.util.ActivityLifecycleCallbacks;
import com.surcumference.fingerprint.util.ApplicationUtils;
import com.surcumference.fingerprint.util.Task;
import com.surcumference.fingerprint.util.Umeng;
import com.surcumference.fingerprint.util.log.L;


/**
 * Created by Jason on 2017/9/8.
 */
public class WeChatPlugin extends WeChatBasePlugin {

    @Keep
    public static void main(String appDataDir) {
        L.d("Xposed plugin init version: " + BuildConfig.VERSION_NAME);
        Task.onApplicationReady(WeChatPlugin::init);
    }

    public static void init() {
        PluginApp.setup(PluginType.Magisk, PluginTarget.WeChat);
        Application application = ApplicationUtils.getApplication();
        WeChatPlugin plugin = new WeChatPlugin();

        Task.onMain(1000, ()-> Umeng.init(application));

        UpdateFactory.lazyUpdateWhenActivityAlive();
        application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityResumed(Activity activity) {
                plugin.onActivityResumed(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                plugin.onActivityPaused(activity);
            }
        });
    }
}
