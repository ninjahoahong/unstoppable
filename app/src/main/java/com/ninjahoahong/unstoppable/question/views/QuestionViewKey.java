package com.ninjahoahong.unstoppable.question.views;

import com.google.auto.value.AutoValue;
import com.ninjahoahong.unstoppable.R;
import com.ninjahoahong.unstoppable.utils.Key;

@AutoValue
public abstract class QuestionViewKey extends Key {
    @Override
    public int layout() {
        return R.layout.question_view;
    }

    public static QuestionViewKey create() {

        return new AutoValue_QuestionViewKey();
    }
}