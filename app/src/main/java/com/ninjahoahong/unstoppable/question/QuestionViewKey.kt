package com.ninjahoahong.unstoppable.question

import com.ninjahoahong.unstoppable.utils.BaseKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionViewKey(
    val tag: String
) : BaseKey() {

    constructor() : this("QuestionViewKey")

    override fun createFragment() = QuestionViewFragment()
}