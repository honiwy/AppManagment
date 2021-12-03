package com.system.appmanagement

import android.graphics.drawable.Drawable

data class App(
    val packageName: String,
    val appName: String,
    val flags: Int,
    val icon: Drawable
)
