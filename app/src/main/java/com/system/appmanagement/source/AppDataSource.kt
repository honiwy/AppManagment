package com.system.appmanagement.source

import android.content.Context
import androidx.lifecycle.LiveData
import com.system.appmanagement.data.AppDatabase
import com.system.appmanagement.data.AppInDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppDataSource(val context: Context) {
    suspend fun insertBlockApp(appInDatabase: AppInDatabase) {
        return withContext(Dispatchers.IO) {
            AppDatabase.getInstance(context).appDatabaseDao.insertApp(appInDatabase)
        }
    }

    fun getAppCollected(): LiveData<List<AppInDatabase>> {
        return AppDatabase.getInstance(context).appDatabaseDao.getAppList()
    }

    suspend fun updateBlockStatus(appInDatabase: AppInDatabase){
        return withContext(Dispatchers.IO) {
            AppDatabase.getInstance(context).appDatabaseDao.update(appInDatabase)
        }
    }
}