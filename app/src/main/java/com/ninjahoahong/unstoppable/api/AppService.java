package com.ninjahoahong.unstoppable.api;


import com.ninjahoahong.unstoppable.data.models.Question;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface AppService {
    @GET("api.php?amount=1&difficult=hard&type=multiple")
    Observable<Question> getQuestions();
}
