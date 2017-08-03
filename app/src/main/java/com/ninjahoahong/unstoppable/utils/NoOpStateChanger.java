package com.ninjahoahong.unstoppable.utils;

import com.zhuinden.simplestack.StateChange;
import com.zhuinden.simplestack.StateChanger;

public class NoOpStateChanger implements StateChanger {
    @Override
    public void handleStateChange(StateChange stateChange, Callback completionCallback) {
        completionCallback.stateChangeComplete();
    }
}