package com.ninjahoahong.unstoppable

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ninjahoahong.unstoppable.api.AppService
import com.ninjahoahong.unstoppable.utils.SchedulerProvider
import com.ninjahoahong.unstoppable.utils.SchedulerProviderImpl
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun applicationContext(): Context = context

    @Provides
    fun scheduler(): SchedulerProvider = SchedulerProviderImpl()

    @Provides
    fun compositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    @Singleton
    fun appService(gson: Gson, okHttpClient: OkHttpClient): AppService = Retrofit.Builder().apply {
        baseUrl(BuildConfig.API_BASE_URL)
        addConverterFactory(GsonConverterFactory.create(gson))
        addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        client(okHttpClient)
    }.build().create(AppService::class.java)

    @Provides
    @Singleton
    fun gson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun httpCache(context: Context): Cache = Cache(context.cacheDir, 10000)

    @Provides
    @Singleton
    fun okhttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder().apply {
        cache(cache)
        connectTimeout(10, TimeUnit.DAYS)
        readTimeout(10, TimeUnit.DAYS)
        addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
    }.build()
}

