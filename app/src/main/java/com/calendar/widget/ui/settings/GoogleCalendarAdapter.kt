package com.calendar.widget.ui.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.calendar.widget.databinding.ItemGoogleCalendarBinding
import com.google.api.services.calendar.model.CalendarListEntry

class GoogleCalendarAdapter(
    private val onToggle: (String, Boolean) -> Unit
) : ListAdapter<CalendarListEntry, GoogleCalendarAdapter.ViewHolder>(DiffCallback) {

    private var selectedIds = setOf<String>()

    fun setSelectedIds(ids: List<String>) {
        selectedIds = ids.toSet()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGoogleCalendarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = getItem(position)
        val entryId = entry.id ?: return // Skip entries with null ID
        val isSelected = selectedIds.contains(entryId) || (selectedIds.isEmpty() && entry.primary == true)
        holder.bind(entry, entryId, isSelected)
    }

    inner class ViewHolder(private val binding: ItemGoogleCalendarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: CalendarListEntry, entryId: String, isSelected: Boolean) {
            binding.tvCalendarName.text = entry.summary ?: "Untitled"
            binding.tvCalendarDescription.text = entry.description ?: entryId
            
            try {
                val color = android.graphics.Color.parseColor(entry.backgroundColor)
                binding.viewCalendarColor.setBackgroundColor(color)
            } catch (e: Exception) {
                binding.viewCalendarColor.setBackgroundColor(android.graphics.Color.GRAY)
            }

            binding.switchSelected.setOnCheckedChangeListener(null)
            binding.switchSelected.isChecked = isSelected
            
            binding.switchSelected.setOnCheckedChangeListener { _, isChecked ->
                onToggle(entryId, isChecked)
            }
            
            binding.root.setOnClickListener {
                binding.switchSelected.toggle()
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CalendarListEntry>() {
        override fun areItemsTheSame(oldItem: CalendarListEntry, newItem: CalendarListEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CalendarListEntry, newItem: CalendarListEntry): Boolean {
            return oldItem.summary == newItem.summary && oldItem.description == newItem.description
        }
    }
}