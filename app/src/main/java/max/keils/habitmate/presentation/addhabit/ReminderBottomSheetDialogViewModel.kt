package max.keils.habitmate.presentation.addhabit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import max.keils.domain.model.DaysOfWeek
import max.keils.domain.model.Reminder
import max.keils.domain.model.ReminderTime
import javax.inject.Inject

class ReminderBottomSheetDialogViewModel @Inject constructor() : ViewModel() {

    private val _reminder = MutableLiveData(
        Reminder(
            daysOfWeek = DaysOfWeek.MONDAY,
            time = ReminderTime(DEFAULT_HOUR, DEFAULT_MINUTE)
        )
    )
    val reminder: LiveData<Reminder>
        get() = _reminder

    fun setReminder(reminder: Reminder) {
        _reminder.value = reminder
    }

    fun updateSelectedTime(hour: Int, minute: Int) {
        _reminder.value = _reminder.value?.let {
            it.copy(
                time = it.time.copy(hour, minute)
            )
        }
    }

    fun updateSelectedDay(dayNumber: Int) {
        _reminder.value = _reminder.value?.copy(
            daysOfWeek = DaysOfWeek.fromNumber(dayNumber)
        )
    }

    companion object {
        const val DEFAULT_HOUR = 10
        const val DEFAULT_MINUTE = 0
    }

}