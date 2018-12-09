package com.ninjahoahong.unstoppable

import android.support.v4.app.Fragment
import com.ninjahoahong.unstoppable.utils.BaseKey
import com.ninjahoahong.unstoppable.utils.requireArguments

open class BaseFragment : Fragment() {
    fun <T : BaseKey> getKey(): T = requireArguments.getParcelable<T>("KEY")
}