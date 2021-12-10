package com.system.appmanagement.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.system.appmanagement.block.BlockViewModel
import com.system.appmanagement.display.DisplayViewModel
import com.system.appmanagement.source.AppRepository

@Suppress("UNCHECKED_CAST")
    class ViewModelFactory(
        private val appRepository: AppRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>) =
            with(modelClass) {
                when {
                    isAssignableFrom(DisplayViewModel::class.java) ->
                        DisplayViewModel(appRepository)
                    isAssignableFrom(BlockViewModel::class.java) ->
                        BlockViewModel(appRepository)
                    else ->
                        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            } as T
    }