package max.keils.habitmate.presentation.habitlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import max.keils.domain.model.Habit
import max.keils.domain.usecase.GetHabitsUseCase
import max.keils.domain.usecase.SaveHabitUseCase
import javax.inject.Inject

class HabitListViewModel @Inject constructor(
    private val saveHabitUseCase: SaveHabitUseCase,
    getHabitsUseCase: GetHabitsUseCase
) : ViewModel() {

    val habits = getHabitsUseCase()

    fun changeState(habit: Habit) {
        viewModelScope.launch {
            habit.copy(
                isCompletedToday = !habit.isCompletedToday
            ).also {
                saveHabitUseCase.invoke(it)
            }
        }
    }
}