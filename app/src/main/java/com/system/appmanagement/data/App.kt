package com.system.appmanagement.data

import android.graphics.drawable.Drawable

data class App(
    val packageName: String,
    val appName: String,
    val flags: Int,
    val icon: Drawable
)
