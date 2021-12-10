package com.system.appmanagement.block

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.system.appmanagement.data.AppInDatabase
import com.system.appmanagement.databinding.ItemAppInDatabaseBinding

class BlockAppAdapter (val viewModel: BlockViewModel) : ListAdapter<AppInDatabase, BlockAppAdapter.AppViewHolder>(
    DiffCallback
) {

    class AppViewHolder(private var binding: ItemAppInDatabaseBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(product: AppInDatabase, viewModel: BlockViewModel) {

            binding.app = product
            binding.viewModel = viewModel
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<AppInDatabase>() {
        override fun areItemsTheSame(oldItem: AppInDatabase, newItem: AppInDatabase): Boolean {
            return (oldItem.packageName == newItem.packageName)
        }

        override fun areContentsTheSame(oldItem: AppInDatabase, newItem: AppInDatabase): Boolean {
            return oldItem.isBlocked == newItem.isBlocked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder(ItemAppInDatabaseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }
}
