package com.example.taskapp53.utils

import android.content.Context
import android.util.Log

class Preferences(context: Context) {
    private val sharedPreference = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    var imgUri: String
        set(value) = sharedPreference.edit().putString("imgUri", value).apply()
        get() = sharedPreference.getString(
            "imgUri",
            "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg?20200913095930"
        ).toString()

    fun setBoardingShowed(isShow: Boolean) {
        sharedPreference.edit().putBoolean("board", isShow).apply()
    }

    fun isBoardingShowed(): Boolean {
        return sharedPreference.getBoolean("board", false)
    }
}