package max.keils.habitmate.presentation.habitlist

import androidx.recyclerview.widget.RecyclerView
import max.keils.domain.model.Habit
import max.keils.habitmate.databinding.HabitItemBinding

class HabitViewHolder(private val binding: HabitItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Habit) {
            with(binding) {
                tvHabitName.text = item.name
                tvDescription.text = item.description
                rbState.isSelected = item.isCompletedToday
            }
        }
}
