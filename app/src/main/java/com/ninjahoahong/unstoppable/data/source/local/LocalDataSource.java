package com.ninjahoahong.unstoppable.data.source.local;

import com.ninjahoahong.unstoppable.AndroidApp;
import com.ninjahoahong.unstoppable.api.AppService;
import com.ninjahoahong.unstoppable.data.models.Question;
import com.ninjahoahong.unstoppable.data.source.DataSource;

import javax.inject.Inject;

import io.reactivex.Observable;


public class LocalDataSource implements DataSource {

    @Inject
    AppService appService;

    public LocalDataSource() {
        AndroidApp.getAppComponent().inject(this);
    }

    @Override
    public Observable<Question> getQuestions() {
        return appService.getQuestions();
    }
}
