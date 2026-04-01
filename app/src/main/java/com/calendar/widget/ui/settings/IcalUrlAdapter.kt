package com.calendar.widget.ui.settings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.calendar.widget.R

/**
 * RecyclerView adapter for displaying iCal URLs with delete buttons.
 */
class IcalUrlAdapter(
    private val onDeleteClick: (String) -> Unit
) : ListAdapter<String, IcalUrlAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ical_url, parent, false)
        return ViewHolder(view, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        itemView: View,
        private val onDeleteClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        
        private val tvUrl: TextView = itemView.findViewById(R.id.tv_url)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btn_delete)

        fun bind(url: String) {
            tvUrl.text = url
            btnDelete.setOnClickListener {
                onDeleteClick(url)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
