package com.system.appmanagement.extension

import androidx.fragment.app.Fragment
import com.system.appmanagement.AppManagementApplication
import com.system.appmanagement.factory.ViewModelFactory

fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as AppManagementApplication).appRepository
    return ViewModelFactory(repository)
}