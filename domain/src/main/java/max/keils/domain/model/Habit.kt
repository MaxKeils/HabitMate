package max.keils.domain.model

data class Habit(
    val id: Int = UNDEFINED_ID,
    val name: String,
    val description: String,
    val frequency: Int,
    val isCompletedToday: Boolean,
    val reminders: List<Reminder> = listOf()
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
