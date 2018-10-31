package com.example.administrator.easycure;

import android.app.Application;

import com.mob.MobSDK;
import com.mob.commons.SMSSDK;

import org.litepal.LitePal;

/**
 * Created by Administrator on 2018/10/26 0026.
 */

public class EasyCureApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        MobSDK.init(getApplicationContext());

    }
}
