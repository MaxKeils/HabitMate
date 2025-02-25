package max.keils.habitmate.presentation.habitlist

import androidx.recyclerview.widget.DiffUtil
import max.keils.domain.model.Habit

class DiffUtilHabit : DiffUtil.ItemCallback<Habit>() {
    override fun areItemsTheSame(oldItem: Habit, newItem: Habit) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Habit, newItem: Habit) = oldItem == newItem
}