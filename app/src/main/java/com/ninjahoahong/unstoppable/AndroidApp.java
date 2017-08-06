package com.ninjahoahong.unstoppable;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.ninjahoahong.unstoppable.utils.ForceUpdateChecker;

import java.util.HashMap;
import java.util.Map;


public class AndroidApp extends Application {

    private static AppComponent appComponent;
    private static final String TAG = AndroidApp.class.getSimpleName();
    private static final long FETCHING_DATA_PERIODICITY_IN_SECOND = 30;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule((getApplicationContext())))
                .netModule(new NetModule(BuildConfig.API_BASE_URL))
                .build();

        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        // set in-app defaults
        Map<String, Object> remoteConfigDefaults = new HashMap<>();
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_REQUIRED, false);
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_CURRENT_VERSION, "0.0.1");
        remoteConfigDefaults.put(ForceUpdateChecker.KEY_UPDATE_URL,
                "https://play.google.com/store/apps/details?id=com.ninjahoahong.unstoppable");

        firebaseRemoteConfig.setDefaults(remoteConfigDefaults);
        firebaseRemoteConfig.fetch(FETCHING_DATA_PERIODICITY_IN_SECOND)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "remote config is fetched.");
                        firebaseRemoteConfig.activateFetched();
                    }
                });
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static Context getApplicaitonContext() {
        return getApplicaitonContext();
    }
}
