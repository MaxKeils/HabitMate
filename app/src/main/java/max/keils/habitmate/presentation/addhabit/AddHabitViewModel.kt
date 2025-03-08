package max.keils.habitmate.presentation.addhabit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import max.keils.domain.model.Habit
import max.keils.domain.model.Reminder
import max.keils.domain.usecase.AddHabitUseCase
import max.keils.domain.usecase.GetHabitByIdUseCase
import max.keils.domain.usecase.UpdateHabitUseCase
import javax.inject.Inject

class AddHabitViewModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val getHabitByIdUseCase: GetHabitByIdUseCase,
) : ViewModel() {

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _habitItem = MutableLiveData<Habit>()
    val habitItem: LiveData<Habit>
        get() = _habitItem

    private val _reminders = MutableLiveData<List<Reminder>>(emptyList())
    val reminders: LiveData<List<Reminder>>
        get() = _reminders

    fun addReminder(reminder: Reminder) {
        val currentList = _reminders.value.orEmpty()
        val newList = currentList.toMutableList().apply { add(reminder) }
        _reminders.value = newList
    }
    fun addHabit(newHabit: Habit) {
        viewModelScope.launch {
            addHabitUseCase(newHabit)
            finishWork()
        }
    }

    fun updateHabit(newHabit: Habit) {
        habitItem.value?.let {
            viewModelScope.launch {
                updateHabitUseCase(newHabit)
                finishWork()
            }
        }
    }

    fun loadHabitById(habitId: Int) {
        viewModelScope.launch {
            getHabitByIdUseCase(habitId)?.let {
                _habitItem.value = it
            } ?: throw RuntimeException("HabitId is null. Couldn't find a habit")
        }
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}