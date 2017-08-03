package com.ninjahoahong.unstoppable.utils.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler background();

    @NonNull
    Scheduler main();
}
