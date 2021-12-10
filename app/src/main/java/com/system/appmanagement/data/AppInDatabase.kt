package com.system.appmanagement.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_in_list", primaryKeys = ["app_package_name"])
data class AppInDatabase(
    @ColumnInfo(name = "app_package_name")
    val packageName: String,
    @ColumnInfo(name = "app_name")
    val appName: String,
    @ColumnInfo(name = "app_flags")
    val flags: Int,
    @ColumnInfo(name = "is_blocked")
    var isBlocked: Boolean=false,
    @ColumnInfo(name = "is_system_app")
    var isSystemApp: Boolean=false
){
    fun setBlockedStatus(isBlocked:Boolean){
        this.isBlocked = isBlocked
    }

    fun setAppCategory(isSystemApp:Boolean){
        this.isSystemApp = isSystemApp
    }
}
