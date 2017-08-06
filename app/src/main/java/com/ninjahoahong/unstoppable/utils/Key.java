package com.ninjahoahong.unstoppable.utils;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.zhuinden.simplestack.navigator.StateKey;
import com.zhuinden.simplestack.navigator.ViewChangeHandler;
import com.zhuinden.simplestack.navigator.changehandlers.SegueViewChangeHandler;

public abstract class Key implements StateKey, Parcelable {
    @NonNull
    @Override
    public ViewChangeHandler viewChangeHandler() {
        return new SegueViewChangeHandler();
    }

    public abstract int layout();
}