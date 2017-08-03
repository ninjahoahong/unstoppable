package com.ninjahoahong.unstoppable;

import android.content.Context;

import com.ninjahoahong.unstoppable.data.source.QuestionRepository;
import com.ninjahoahong.unstoppable.data.source.ScheduleRepositoryModule;
import com.ninjahoahong.unstoppable.data.source.local.LocalDataSource;
import com.ninjahoahong.unstoppable.data.source.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ScheduleRepositoryModule.class, AppModule.class, NetModule.class})
public interface AppComponent {

    void inject(RemoteDataSource remoteDataSource);
    void inject(LocalDataSource localDataSource);
    QuestionRepository getScheduleRepository();
    Context getContext();
}
