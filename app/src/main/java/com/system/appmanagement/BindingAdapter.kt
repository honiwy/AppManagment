package com.system.appmanagement

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("appItems")
fun bindRecyclerViewWithAppItems(recyclerView: RecyclerView, appItems: List<App>?) {
    appItems?.let {
        recyclerView.adapter?.apply {
            when (this) {
                is AppAdapter -> submitList(it)
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