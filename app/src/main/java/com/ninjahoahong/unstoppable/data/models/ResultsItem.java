package com.ninjahoahong.unstoppable.data.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class ResultsItem implements Parcelable {

    @SerializedName("difficulty")
    public abstract String difficulty();

    @SerializedName("question")
    public abstract String question();

    @SerializedName("correct_answer")
    public abstract String correctAnswer();

    @SerializedName("incorrect_answers")
    public abstract List<String> incorrectAnswers();

    @SerializedName("category")
    public abstract String category();

    @SerializedName("type")
    public abstract String type();

    public static TypeAdapter<ResultsItem> typeAdapter(Gson gson) {
        return new AutoValue_ResultsItem.GsonTypeAdapter(gson);
    }
}