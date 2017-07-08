/*package com.example.lwxwl.coin.Model;

import android.content.Context;
import android.provider.Settings;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class CoinApplication extends android.app.Application {

    public static final int VERSION = 120;

    private static Context mContext;


    public static RefWatcher getRefWatcher(Context context) {
        CoinApplication application = (CoinApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();

        refWatcher = LeakCanary.install(this);
        CoinApplication.mContext = getApplicationContext();
    }

    public static Context getAppContext() {
        return CoinApplication.mContext;
    }

    public static String getAndroidId() {
        return Settings.Secure.getString(
                getAppContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

}
*/
