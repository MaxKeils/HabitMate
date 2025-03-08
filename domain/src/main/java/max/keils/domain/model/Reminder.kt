package max.keils.domain.model

import java.util.UUID

data class Reminder(
    val id: Int = UUID.randomUUID().hashCode(),
    val daysOfWeek: DaysOfWeek,
    val time: ReminderTime
)