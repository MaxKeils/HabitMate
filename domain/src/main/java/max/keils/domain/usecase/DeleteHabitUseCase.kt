package max.keils.domain.usecase

import max.keils.domain.repository.HabitRepository

class DeleteHabitUseCase(private val repository: HabitRepository) {

    suspend operator fun invoke(habitId: Int) {
        repository.deleteHabit(habitId)
    }

}