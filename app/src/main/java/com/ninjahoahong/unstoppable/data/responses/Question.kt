package com.ninjahoahong.unstoppable.data.responses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Question(
        val responseCode: Int,
        val results: List<ResultsItem>
) : Parcelable