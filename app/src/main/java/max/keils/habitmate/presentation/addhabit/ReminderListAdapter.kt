package max.keils.habitmate.presentation.addhabit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import max.keils.domain.model.Reminder
import max.keils.habitmate.databinding.ReminderItemBinding
import javax.inject.Inject

class ReminderListAdapter @Inject constructor() :
    ListAdapter<Reminder, ReminderViewHolder>(DiffUtilReminder()) {

    var onEditHabitClickListener: ((Reminder) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val item = ReminderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReminderViewHolder(item)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.ivEdit.setOnClickListener {
            onEditHabitClickListener?.invoke(item)
        }

        holder.bind(item)
    }
}