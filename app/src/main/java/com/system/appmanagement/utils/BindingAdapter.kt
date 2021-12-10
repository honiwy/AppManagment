package com.system.appmanagement.utils

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView
import com.system.appmanagement.block.BlockAppAdapter
import com.system.appmanagement.data.App
import com.system.appmanagement.data.AppInDatabase
import com.system.appmanagement.display.DisplayAppAdapter

@BindingAdapter("displayApps")
fun bindRecyclerViewWithAppItems(recyclerView: RecyclerView, displayApps: List<App>?) {
    displayApps?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is DisplayAppAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("blockApps")
fun bindRecyclerViewWithApps(recyclerView: RecyclerView, blockApps: List<AppInDatabase>?) {
    blockApps?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is BlockAppAdapter -> submitList(it)
            }
        }
    }
}

@BindingAdapter("selectionPositionAttrChanged")
fun setSpinnerOnChangeListener(spinner: Spinner, listener: InverseBindingListener) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }
}