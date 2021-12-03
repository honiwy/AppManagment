package com.system.appmanagement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.system.appmanagement.databinding.ItemAppBinding

class AppAdapter(
    private val onClickListener: OnClickListener,
    private val onLongClickListener: OnLongClickListener
) : ListAdapter<App, AppAdapter.AppViewHolder>(DiffCallback) {

    class OnClickListener(val clickListener: (app: App) -> Unit) {
        fun onClick(app: App) = clickListener(app)
    }

    class OnLongClickListener(val clickLongListener: (app: App) -> Unit) {
        fun onLongClick(app: App) = clickLongListener(app)
    }

    class AppViewHolder(private var binding: ItemAppBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            app: App, onClickListener: OnClickListener,
            onLongClickListener: OnLongClickListener
        ) {
            binding.app = app
            binding.root.setOnClickListener {
                onClickListener.onClick(app)
            }
            binding.root.setOnLongClickListener {
                onLongClickListener.onLongClick(app)
                true
            }
            binding.executePendingBindings()
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<App>() {
        override fun areItemsTheSame(oldItem: App, newItem: App): Boolean {
            return (oldItem === newItem)
        }

        override fun areContentsTheSame(
            oldItem: App,
            newItem: App
        ): Boolean {
            return oldItem.packageName == newItem.packageName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        return AppViewHolder(
            ItemAppBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(
            getItem(position), onClickListener,
            onLongClickListener
        )
    }
}