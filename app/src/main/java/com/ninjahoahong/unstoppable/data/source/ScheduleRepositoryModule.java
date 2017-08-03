package com.ninjahoahong.unstoppable.data.source;

import com.ninjahoahong.unstoppable.data.source.remote.RemoteDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ScheduleRepositoryModule {

    @Singleton
    @Provides
    DataSource providePostsRemoteDataSource() {
        return new RemoteDataSource();
    }

}
