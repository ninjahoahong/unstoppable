package com.ninjahoahong.unstoppable.question;

import android.content.Context;

import com.ninjahoahong.unstoppable.data.source.QuestionRepository;
import com.ninjahoahong.unstoppable.utils.schedulers.BaseSchedulerProvider;
import com.ninjahoahong.unstoppable.utils.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionModule {

    private final QuestionContract.View view;

    public QuestionModule(QuestionContract.View view) {
        this.view = view;
    }

    @Provides
    QuestionContract.Presenter providePresenter(QuestionContract.View view,
                                                QuestionRepository questionRepository,
                                                BaseSchedulerProvider baseSchedulerProvider,
                                                Context context) {
        return new QuestionPresenter(questionRepository, view,
                baseSchedulerProvider);
    }

    @Provides
    QuestionContract.View provideView() {
        return view;
    }

    @Provides
    BaseSchedulerProvider provideScheduler() {
        return SchedulerProvider.getInstance();
    }

}
