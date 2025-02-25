package max.keils.habitmate.presentation.habitslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import max.keils.domain.model.Habit
import max.keils.domain.usecase.AddHabitUseCase
import max.keils.domain.usecase.GetHabitsUseCase
import javax.inject.Inject

class HabitsListViewModel @Inject constructor(
    getHabitsUseCase: GetHabitsUseCase,
    private val addHabitUseCase: AddHabitUseCase
) : ViewModel() {

    val habits = getHabitsUseCase()

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            addHabitUseCase(habit)
        }
    }
}