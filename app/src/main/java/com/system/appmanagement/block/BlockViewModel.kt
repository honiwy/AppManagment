package com.system.appmanagement.block

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.appmanagement.data.App
import com.system.appmanagement.data.AppInDatabase
import com.system.appmanagement.source.AppRepository
import com.system.appmanagement.utils.SharedPreference
import kotlinx.coroutines.launch

class BlockViewModel(private val appRepository: AppRepository) : ViewModel() {

    val displayedApp: LiveData<List<AppInDatabase>> = appRepository.getAppCollected()

    private val _isBlockedEnabled = MutableLiveData<Boolean>()
    val isBlockedEnabled: LiveData<Boolean> = _isBlockedEnabled

    init {
        _isBlockedEnabled.value = SharedPreference.blockStatus
    }

    fun updateBlockStatus(app: AppInDatabase) {
        app.isBlocked = !app.isBlocked
        viewModelScope.launch {
            appRepository.updateBlockStatus(app)
        }
    }

    fun changeBlockState(isChecked: Boolean) {
        Log.d("BlockViewModel", "changeBlockState: $isChecked")
        SharedPreference.blockStatus = isChecked
    }
}