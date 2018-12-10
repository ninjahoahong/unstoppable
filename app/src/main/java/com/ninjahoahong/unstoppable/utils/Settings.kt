package com.ninjahoahong.unstoppable.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.GsonBuilder

class Settings {

    companion object {
        private val DEFAULTS = arrayListOf<Setting>()
        private const val IDENTIFIER = "unstoppable_preferences"
        const val FIRST_TIME_PLAY = "FirstTimePlay"
        init {
            DEFAULTS.add(object : Setting(FIRST_TIME_PLAY, false, Boolean::class.java) {
                override fun createDescription(context: Context): String? {
                    return null
                }
            })
        }

        fun isNot(context: Context, key: String): Boolean {
            return !isTrue(context, key)
        }

        private fun isTrue(context: Context, key: String): Boolean {
            return Settings.getSettings(context).getBoolean(key, false)
        }

        private fun getLong(context: Context, key: String): Long? {
            val s = Settings.getSettings(context)
            return if (s.contains(key)) {
                s.getLong(key, 0)
            } else {
                null
            }
        }


        operator fun get(context: Context, key: String): String? {
            return get(context, key, "")
        }

        operator fun get(context: Context, key: String, defaultValue: String?): String? {
            return Settings.getSettings(context).getString(key, defaultValue)
        }

        operator fun set(context: Context, key: String, value: String?) {
            val editor = Settings.getSettings(context).edit()
            editor.putString(key, value)
            editor.apply()
        }

        operator fun set(context: Context, key: String, value: Boolean) {
            val editor = Settings.getSettings(context).edit()
            editor.putBoolean(key, value)
            editor.apply()
        }

        fun delete(context: Context, key: String) {
            val editor = Settings.getSettings(context).edit()
            editor.remove(key)
            editor.commit()
        }

        private fun getSettings(context: Context): SharedPreferences {
            return context.getSharedPreferences(IDENTIFIER,
                Context.MODE_PRIVATE)
        }

        fun getAny(context: Context, key: String, valueClass: Class<*>): Any? {
            return when (valueClass) {
                String::class.java -> Settings[context, key]
                Boolean::class.java -> Settings.isTrue(context, key)
                Long::class.java -> Settings.getLong(context, key)
                else -> Settings.getObject(key, valueClass, context)
            }
        }

        private fun <E> getObject(key: String, clazz: Class<E>, context: Context): E {
            val json = Settings[context, key, null]
            return GsonBuilder().create().fromJson(json, clazz)
        }

        fun contains(context: Context, key: String): Boolean = getSettings(context).contains(key)


        private fun setLong( context: Context, key: String, value: Long) {
            val editor = Settings.getSettings(context).edit()
            editor.putLong(key, value)
            editor.apply()
        }

        private fun <E> setObject( key: String, theObject: E, context: Context) {
            Settings[context, key] = GsonBuilder().create().toJson(theObject)
        }

        private fun setAny( context: Context, key: String, value: Any?, valueClass: Class<*>) {
            when (valueClass) {
                String::class.java -> Settings[context, key] = value as String?
                Boolean::class.java -> Settings[context, key] = value as Boolean
                Long::class.java -> Settings.setLong(context, key, value as Long)
                else -> Settings.setObject(key, value, context) }
        }

        fun setDefaultsIfNotSet(context: Context) {
            for (setting in DEFAULTS) {
                if (!Settings.contains(context, setting.key)) {
                    Settings.setAny(context, setting.key, setting.defaultValue, setting.valueClass)
                }
            }
        }
    }
}