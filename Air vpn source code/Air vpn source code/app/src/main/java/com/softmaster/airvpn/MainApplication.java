package com.softmaster.airvpn;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


public class MainApplication  extends MultiDexApplication {

    private static final String CHANNEL_ID = "airvpn";

    private static Context context;


    public static Context getApplication() {
        return context;
    }

    public static Context getStaticContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
    }
}
