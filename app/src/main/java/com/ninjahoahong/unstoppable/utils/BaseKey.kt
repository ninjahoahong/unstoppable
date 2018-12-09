package com.ninjahoahong.unstoppable.utils

import android.os.Bundle
import android.os.Parcelable
import com.ninjahoahong.unstoppable.BaseFragment

abstract class BaseKey : Parcelable {
    val fragmentTag: String
        get() = toString()

    fun newFragment(): BaseFragment = createFragment().apply {
        arguments = (arguments ?: Bundle()).also { bundle ->
            bundle.putParcelable("KEY", this@BaseKey)
        }
    }

    protected abstract fun createFragment(): BaseFragment
}