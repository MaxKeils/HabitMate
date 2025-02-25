package max.keils.domain.usecase

import max.keils.domain.model.Habit
import max.keils.domain.repository.HabitRepository
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(private val repository: HabitRepository) {

    suspend operator fun invoke(habit: Habit) {
        repository.addHabit(habit)
    }

}