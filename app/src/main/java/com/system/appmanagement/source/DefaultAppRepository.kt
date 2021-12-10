package com.system.appmanagement.source

import androidx.lifecycle.LiveData
import com.system.appmanagement.data.AppInDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultAppRepository(private val appDataSource: AppDataSource) : AppRepository {
    override suspend fun insertBlockApp(appInDatabase: AppInDatabase) {
        appDataSource.insertBlockApp(appInDatabase)
    }

    override fun getAppCollected(): LiveData<List<AppInDatabase>> {
        return appDataSource.getAppCollected()
    }

    override suspend fun updateBlockStatus(appInDatabase: AppInDatabase){
        appDataSource.updateBlockStatus(appInDatabase)
    }
}