package com.ninjahoahong.unstoppable.data.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultsItem(
        val difficulty: String,
        val question: String,
        @SerializedName("correct_answer") val correctAnswer: String,
        @SerializedName("incorrect_answers") val inCorrectAnswers: List<String>,
        @SerializedName("category") val category: String,
        @SerializedName("type") val type: String
) : Parcelable