package com.system.appmanagement.utils

import android.content.Context
import com.system.appmanagement.AppManagementApplication

object SharedPreference {
    private const val APP_DATA = "app_data"
    private const val BLOCK_STATUS = "block_status"

    var blockStatus: Boolean? = null
        get() = AppManagementApplication.instance
            .getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
            .getBoolean(BLOCK_STATUS, true)
        set(value) {
            field = when (value) {
                null -> {
                    AppManagementApplication.instance
                        .getSharedPreferences(APP_DATA, Context.MODE_PRIVATE).edit()
                        .remove(BLOCK_STATUS)
                        .apply()
                    null
                }
                else -> {
                    AppManagementApplication.instance
                        .getSharedPreferences(APP_DATA, Context.MODE_PRIVATE).edit()
                        .putBoolean(BLOCK_STATUS, value)
                        .apply()
                    value
                }
            }
        }
}