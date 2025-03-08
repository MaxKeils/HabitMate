package max.keils.habitmate.presentation.addhabit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import max.keils.domain.model.Reminder
import max.keils.habitmate.databinding.ReminderItemBinding
import javax.inject.Inject

class ReminderListAdapter @Inject constructor() :
    ListAdapter<Reminder, ReminderViewHolder>(DiffUtilReminder()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val item = ReminderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("ReminderListAdapter", "onCreateViewHolder")
        return ReminderViewHolder(item)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("ReminderListAdapter", "onBindViewHolder")
        holder.bind(item)
    }
}