package max.keils.habitmate.presentation.addhabit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import max.keils.domain.model.DaysOfWeek
import max.keils.domain.model.ReminderTime
import javax.inject.Inject

class ReminderBottomSheetDialogViewModel @Inject constructor() : ViewModel() {

    private val _selectedTime = MutableLiveData(ReminderTime(DEFAULT_HOUR, DEFAULT_MINUTE))
    val selectedTime: LiveData<ReminderTime>
        get() = _selectedTime

    private val _selectedDay = MutableLiveData(DaysOfWeek.MONDAY)


    val selectedDay: LiveData<DaysOfWeek>
        get() = _selectedDay

    fun updateSelectedTime(hour: Int, minute: Int) {
        _selectedTime.value = _selectedTime.value?.copy(
            hour = hour, minute = minute
        )
    }

    fun updateSelectedDay(dayNumber: Int) {
        _selectedDay.value = DaysOfWeek.fromNumber(dayNumber)
    }

    companion object {
        const val DEFAULT_HOUR = 10
        const val DEFAULT_MINUTE = 0
    }

}