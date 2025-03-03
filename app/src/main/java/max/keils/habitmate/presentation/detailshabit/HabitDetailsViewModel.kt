package max.keils.habitmate.presentation.detailshabit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import max.keils.domain.model.Habit
import max.keils.domain.usecase.GetHabitByIdUseCase
import javax.inject.Inject

class HabitDetailsViewModel @Inject constructor(
    private val getHabitByIdUseCase: GetHabitByIdUseCase
) : ViewModel() {

    private var _habitLD = MutableLiveData<Habit>()
    val habitLD: LiveData<Habit>
        get() = _habitLD

    fun getHabitById(habitId: Int) {
        viewModelScope.launch {
            _habitLD.value =
                getHabitByIdUseCase(habitId) ?: throw RuntimeException("The habit wasn't found")
        }
    }
}