package max.keils.habitmate.presentation.utils

import android.content.Context
import max.keils.domain.model.DaysOfWeek
import max.keils.habitmate.R

fun DaysOfWeek.toLocalizedString(context: Context): String {
    return when (this) {
        DaysOfWeek.MONDAY -> context.getString(R.string.monday)
        DaysOfWeek.TUESDAY -> context.getString(R.string.tuesday)
        DaysOfWeek.WEDNESDAY -> context.getString(R.string.wednesday)
        DaysOfWeek.THURSDAY -> context.getString(R.string.thursday)
        DaysOfWeek.FRIDAY -> context.getString(R.string.friday)
        DaysOfWeek.SATURDAY -> context.getString(R.string.saturday)
        DaysOfWeek.SUNDAY -> context.getString(R.string.sunday)
    }
}