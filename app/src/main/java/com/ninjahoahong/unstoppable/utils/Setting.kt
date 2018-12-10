package com.ninjahoahong.unstoppable.utils

import android.content.Context
import io.reactivex.functions.Function
import java.io.Serializable

abstract class Setting(
    key: String,
    defaultValue: Any?,
    valueClass: Class<*>) : Serializable {

    var key: String = key
    var defaultValue: Any? = defaultValue
    var valueClass: Class<*> = valueClass

    fun getCurrentValue(context: Context): Any? {
        return Settings.getAny(context,
            key,
            valueClass)
    }

    override fun equals(other: Any?): Boolean {
        return key == (other as Setting).key
    }

    abstract fun createDescription(context: Context): String?

    override fun hashCode(): Int {
        return key.hashCode()
    }

    companion object {
        val TO_KEY = Function<Setting, String> { t: Setting -> t.key }
    }
}