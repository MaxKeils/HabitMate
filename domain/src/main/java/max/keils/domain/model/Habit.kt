package max.keils.domain.model

data class Habit(
    val id: Int = 0,
    val name: String,
    val description: String,
    val frequency: Int,
    val isCompletedToday: Boolean
)
