package com.ninjahoahong.unstoppable.data.source;


import com.ninjahoahong.unstoppable.data.models.Question;

import io.reactivex.Observable;


public interface DataSource {

    Observable<Question> getQuestions();
}
