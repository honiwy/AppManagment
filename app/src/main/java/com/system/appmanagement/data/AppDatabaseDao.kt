package com.system.appmanagement.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AppDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertApp(appInDatabase: AppInDatabase)

    @Delete
    fun remoteBlockApp(appInDatabase: AppInDatabase)

    @Update
    fun update(appInDatabase: AppInDatabase)

    @Query("DELETE FROM app_in_list")
    fun clearBlockList()

    @Query("SELECT * FROM app_in_list WHERE is_system_app = :isSystemApp")
    fun getAppList(isSystemApp: Boolean = false): LiveData<List<AppInDatabase>>

}