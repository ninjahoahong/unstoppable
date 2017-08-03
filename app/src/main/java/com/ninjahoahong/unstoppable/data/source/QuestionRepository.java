package com.ninjahoahong.unstoppable.data.source;

import android.support.annotation.NonNull;

import com.ninjahoahong.unstoppable.data.models.Question;

import javax.inject.Inject;

import io.reactivex.Observable;


public class QuestionRepository implements DataSource {

    @NonNull
    private final DataSource remoteDataSource;

    @Inject
    QuestionRepository(@NonNull DataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<Question> getQuestions() {
        return remoteDataSource.getQuestions();
    }

}
