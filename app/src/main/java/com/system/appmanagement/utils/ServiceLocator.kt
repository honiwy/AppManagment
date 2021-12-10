package com.system.appmanagement.utils

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.system.appmanagement.source.AppDataSource
import com.system.appmanagement.source.AppRepository
import com.system.appmanagement.source.DefaultAppRepository

object ServiceLocator {

    @Volatile
    var appRepository: AppRepository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): AppRepository {
        synchronized(this) {
            return appRepository
                ?: appRepository
                ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): AppRepository {
        return DefaultAppRepository(
            createLocalDataSource(context)
        )
    }

    private fun createLocalDataSource(context: Context): AppDataSource {
        return AppDataSource(context)
    }
}