package max.keils.domain.model

import java.util.Locale

data class ReminderTime(
    val hour: Int,
    val minute: Int
) {
    fun toFormattedString() = String.format(Locale.ROOT, "%02d:%02d", hour, minute)
}
