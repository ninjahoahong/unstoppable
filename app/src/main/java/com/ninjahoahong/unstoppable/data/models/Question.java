package com.ninjahoahong.unstoppable.data.models;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Question implements Parcelable {

    @SerializedName("response_code")
    public abstract int responseCode();

    @SerializedName("results")
    public abstract List<ResultsItem> results();

    public static TypeAdapter<Question> typeAdapter(Gson gson) {
        return new AutoValue_Question.GsonTypeAdapter(gson);
    }
}