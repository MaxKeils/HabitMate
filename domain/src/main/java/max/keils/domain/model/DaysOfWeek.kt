package max.keils.domain.model

enum class DaysOfWeek(val numberDayOfTheWeek: Int) {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    companion object {
        fun fromNumber(number: Int) = entries
            .firstOrNull { it.numberDayOfTheWeek == number }

    }
}