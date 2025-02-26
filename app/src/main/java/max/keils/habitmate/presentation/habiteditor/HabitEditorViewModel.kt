package max.keils.habitmate.presentation.habiteditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import max.keils.domain.model.Habit
import max.keils.domain.usecase.AddHabitUseCase
import max.keils.domain.usecase.GetHabitByIdUseCase
import max.keils.domain.usecase.UpdateHabitUseCase
import javax.inject.Inject

class HabitEditorViewModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase,
    private val getHabitByIdUseCase: GetHabitByIdUseCase
) : ViewModel() {

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    private val _habitItem = MutableLiveData<Habit>()
    val habitItem: LiveData<Habit>
        get() = _habitItem

    fun addHabit(name: String, description: String) {
        Habit(
            name = name,
            description = description,
            frequency = 0,
            isCompletedToday = false
        ).also {
            viewModelScope.launch {
                addHabitUseCase(it)
                finishWork()
            }
        }
    }

    fun editHabit(name: String, description: String) {
        habitItem.value?.let {
            viewModelScope.launch {
                val item = it.copy(name = name, description = description)
                updateHabitUseCase(item)
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