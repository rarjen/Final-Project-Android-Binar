package com.example.finalprojectbinar.util

import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceHelper {
    private lateinit var prefs: SharedPreferences
    private const val PREF_NAME = "token"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun read(key: String, value: String? = null): String? {
        return prefs.getString(key, value)
    }

    fun write(key: String, value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, value)
            apply()
            commit()
        }
    }
}