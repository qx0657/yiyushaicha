package com.depressionscreening.yjj;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;

import es.dmoral.toasty.Toasty;

public class MyApplication extends Application {
    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        //开启Log日志
        LogUtils.getConfig().setLogSwitch(BuildConfig.DEBUG);
        LogUtils.getConfig().setGlobalTag("YYSC_TAG");

        //初始化Toasty弹窗
        Toasty.Config.getInstance()
                .tintIcon(true) // optional (apply textColor also to the icon)
                //.setToastTypeface(@NonNull Typeface typeface) // optional
                .setTextSize(16) // optional
                .allowQueue(false) // optional (prevents several Toastys from queuing)
                .apply(); // required
    }

    public static MyApplication getInstance() {
        return mInstance;
    }
}
