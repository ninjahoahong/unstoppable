package com.ninjahoahong.unstoppable.api

import com.ninjahoahong.unstoppable.data.responses.Question
import io.reactivex.Observable
import retrofit2.http.GET

interface AppService {
    @GET("api.php?amount=1&difficult=hard&type=multiple")
    fun getQuestion(): Observable<Question>
}