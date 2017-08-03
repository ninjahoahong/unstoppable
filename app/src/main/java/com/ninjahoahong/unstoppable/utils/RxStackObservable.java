package com.ninjahoahong.unstoppable.utils;

import android.support.annotation.NonNull;

import com.zhuinden.simplestack.Backstack;
import com.zhuinden.simplestack.StateChange;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposables;

public class RxStackObservable
        implements ObservableOnSubscribe<StateChange> {
    private Backstack backstack;

    private RxStackObservable(Backstack backstack) {
        this.backstack = backstack;
    }

    public static Observable<StateChange> create(Backstack backstack) {
        return Observable.create(new RxStackObservable(backstack));
    }

    @Override
    public void subscribe(@NonNull ObservableEmitter<StateChange> emitter)
            throws Exception {
        final Backstack.CompletionListener completionListener = stateChange -> {
            if(!emitter.isDisposed()) {
                emitter.onNext(stateChange);
            }
        };
        emitter.setDisposable(Disposables.fromAction(() -> backstack.removeCompletionListener(completionListener)));
        backstack.addCompletionListener(completionListener);
    }
}