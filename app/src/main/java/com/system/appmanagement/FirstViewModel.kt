package com.system.appmanagement

import android.content.pm.ApplicationInfo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FirstViewModel : ViewModel() {

    private val _systemApp = MutableLiveData<List<App>>()

    private val _userInstalledApp = MutableLiveData<List<App>>()

    private val _displayedApp = MutableLiveData<List<App>>()
    val displayedApp: LiveData<List<App>> = _displayedApp

    val selectedAppCategoryPosition = MutableLiveData<Int>()

    fun separateAppToList(applicationList: List<App>) {
        val installedApps: MutableList<App> = ArrayList()
        val systemApps: MutableList<App> = ArrayList()
        for (app in applicationList) {
            //checks for flags; if flagged, check if updated system app
            when {
                app.flags and ApplicationInfo.FLAG_UPDATED_SYSTEM_APP != 0 -> {
                    installedApps.add(app)
                }
                app.flags and ApplicationInfo.FLAG_SYSTEM != 0 -> {
                    systemApps.add(app)
                }
                app.flags and ApplicationInfo.FLAG_INSTALLED != 0 -> {
                    installedApps.add(app)
                }
                else -> {
                    systemApps.add(app)
                }
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