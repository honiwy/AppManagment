package com.system.appmanagement

import android.app.Application
import com.system.appmanagement.source.AppRepository
import com.system.appmanagement.utils.ServiceLocator
import kotlin.properties.Delegates

class AppManagementApplication : Application() {

    val appRepository: AppRepository
        get() = ServiceLocator.provideTasksRepository(this)

    companion object {
        var instance: AppManagementApplication by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}