package com.ninjahoahong.unstoppable;

import android.app.Application;
import android.content.Context;


public class AndroidApp extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule((getApplicationContext())))
                .netModule(new NetModule(BuildConfig.API_BASE_URL))
                .build();
    }


    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static Context getApplicaitonContext() {
        return getApplicaitonContext();
    }
}
