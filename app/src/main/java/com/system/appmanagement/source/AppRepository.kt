package com.system.appmanagement.source

import androidx.lifecycle.LiveData
import com.system.appmanagement.data.AppInDatabase

interface AppRepository {
    suspend fun insertBlockApp(appInDatabase: AppInDatabase)

    fun getAppCollected(): LiveData<List<AppInDatabase>>

    suspend fun updateBlockStatus(appInDatabase: AppInDatabase)
}