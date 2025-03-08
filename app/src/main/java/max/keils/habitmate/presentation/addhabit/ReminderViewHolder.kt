package max.keils.habitmate.presentation.addhabit

import androidx.recyclerview.widget.RecyclerView
import max.keils.domain.model.Reminder
import max.keils.habitmate.databinding.ReminderItemBinding
import max.keils.habitmate.presentation.utils.toLocalizedString

class ReminderViewHolder(val binding: ReminderItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Reminder) {
        with(binding) {
            tvReminderTime.text = item.time.toFormattedString()
            tvReminderDay.text = item.daysOfWeek.toLocalizedString(binding.root.context)
        }
    }
}