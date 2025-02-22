package max.keils.domain.usecase

import max.keils.domain.model.Habit
import max.keils.domain.repository.HabitRepository

class UpdateHabitUseCase(private val repository: HabitRepository) {

    suspend operator fun invoke(habit: Habit) {
        repository.updateHabit(habit)
    }

}