package max.keils.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Habit(
    val id: Int = 0,
    val name: String,
    val description: String,
    val frequency: Int,
    val reminderTime: LocalTime,
    val createdAt: LocalDate,
    val isCompletedToday: Boolean
)
