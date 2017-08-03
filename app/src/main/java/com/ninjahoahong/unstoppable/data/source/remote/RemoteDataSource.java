package com.ninjahoahong.unstoppable.data.source.remote;

import com.ninjahoahong.unstoppable.AndroidApp;
import com.ninjahoahong.unstoppable.api.AppService;
import com.ninjahoahong.unstoppable.data.models.Question;
import com.ninjahoahong.unstoppable.data.source.DataSource;

import javax.inject.Inject;

import io.reactivex.Observable;


public class RemoteDataSource implements DataSource {

    @Inject
    AppService appService;

    public RemoteDataSource() {
        AndroidApp.getAppComponent().inject(this);
    }

    @Override
    public Observable<Question> getQuestions() {
        return appService.getQuestions();
    }
}
