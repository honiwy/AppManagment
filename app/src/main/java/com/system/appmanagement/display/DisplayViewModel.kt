package com.system.appmanagement.display

import android.content.pm.ApplicationInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.appmanagement.data.App
import com.system.appmanagement.data.AppInDatabase
import com.system.appmanagement.source.AppRepository
import kotlinx.coroutines.launch

class DisplayViewModel(private val appRepository: AppRepository) : ViewModel() {

    private val _systemApp = MutableLiveData<List<App>>()

    private val _userInstalledApp = MutableLiveData<List<App>>()

    private val _displayedApp = MutableLiveData<List<App>>()
    val displayedApp: LiveData<List<App>> = _displayedApp

    val selectedAppCategoryPosition = MutableLiveData<Int>()

    fun separateAppToList(applicationList: List<App>) {
        val installedApps: MutableList<App> = ArrayList()
        val systemApps: MutableList<App> = ArrayList()
        viewModelScope.launch {
            for (app in applicationList) {
                val appInDatabase = AppInDatabase(
                    packageName = app.packageName,
                    appName = app.appName,
                    flags = app.flags
                )
                when {
                    app.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0 -> {
                        installedApps.add(app)
                        appInDatabase.setAppCategory(false)
                    }
                    app.flags and ApplicationInfo.FLAG_SYSTEM != 0 -> {
                        systemApps.add(app)
                        appInDatabase.setAppCategory(true)
                    }
                    app.flags and ApplicationInfo.FLAG_INSTALLED != 0 -> {
                        installedApps.add(app)
                        appInDatabase.setAppCategory(false)
                    }
                    else -> {
                        systemApps.add(app)
                        appInDatabase.setAppCategory(true)
                    }
                }
                appRepository.insertBlockApp(appInDatabase)
            }
        }

        _userInstalledApp.value = installedApps
        _systemApp.value = systemApps
        prepareDisplayApps()
    }

    fun prepareDisplayApps() {
        _displayedApp.value = when (selectedAppCategoryPosition.value) {
            0 -> _userInstalledApp.value
            1 -> _systemApp.value
            else -> _userInstalledApp.value.orEmpty() + _systemApp.value.orEmpty()
        }
    }

}