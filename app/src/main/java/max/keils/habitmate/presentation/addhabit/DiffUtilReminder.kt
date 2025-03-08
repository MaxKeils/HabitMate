package max.keils.habitmate.presentation.addhabit

import androidx.recyclerview.widget.DiffUtil
import max.keils.domain.model.Reminder

class DiffUtilReminder : DiffUtil.ItemCallback<Reminder>() {
    override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder) = oldItem == newItem
}