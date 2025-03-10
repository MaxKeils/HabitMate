package max.keils.habitmate.presentation.addhabit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import max.keils.domain.model.DaysOfWeek
import max.keils.domain.model.Habit
import max.keils.domain.model.Reminder
import max.keils.domain.model.ReminderTime
import max.keils.domain.usecase.GetHabitByIdUseCase
import max.keils.domain.usecase.SaveHabitUseCase
import javax.inject.Inject

class AddHabitViewModel @Inject constructor(
    private val saveHabitUseCase: SaveHabitUseCase,
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

    fun addReminder(id: Int, day: DaysOfWeek, time: ReminderTime) {
        _reminders.value = _reminders.value?.filter { it.id != id }?.plus(Reminder(id, day, time))
    }

    fun removeReminder(id: Int) {
        _reminders.value = _reminders.value?.filter { it.id != id }
    }

    fun getReminderById(id: Int): Reminder? = reminders.value?.find { it.id == id }

    fun saveHabit(name: String, description: String) {
        viewModelScope.launch {
            val reminders = _reminders.value ?: emptyList()

            val habit = _habitItem.value?.copy(
                name = name,
                description = description,
                reminders = reminders
            ) ?: Habit(
                name = name,
                description = description,
                reminders = reminders,
                frequency = 1,
                isCompletedToday = false
            )

            saveHabitUseCase(habit)
            finishWork()
        }
    }

    fun loadHabitById(habitId: Int) {
        viewModelScope.launch {
            getHabitByIdUseCase(habitId)?.let {
                _habitItem.value = it
                _reminders.value = it.reminders
            } ?: throw RuntimeException("HabitId is null. Couldn't find a habit")
        }
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}