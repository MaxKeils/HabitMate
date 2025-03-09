package max.keils.habitmate.presentation.habitlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import max.keils.domain.model.Habit
import max.keils.habitmate.databinding.HabitItemBinding
import javax.inject.Inject

class HabitListAdapter @Inject constructor() :
    ListAdapter<Habit, HabitViewHolder>(DiffUtilHabit()) {

    var onHabitItemClickListener: ((Habit) -> Unit)? = null
    var onHabitCheckBoxClickListener: ((Habit) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = HabitItemBinding.inflate(inflater, parent, false)
        return HabitViewHolder(view)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.cbState.setOnClickListener { onHabitCheckBoxClickListener?.invoke(item) }
        holder.binding.layout.setOnClickListener { onHabitItemClickListener?.invoke(item) }

        holder.bind(item)
    }
}