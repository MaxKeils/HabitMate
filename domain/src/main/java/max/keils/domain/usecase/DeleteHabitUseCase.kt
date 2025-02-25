package max.keils.domain.usecase

import max.keils.domain.repository.HabitRepository
import javax.inject.Inject

class DeleteHabitUseCase @Inject constructor(private val repository: HabitRepository) {

    suspend operator fun invoke(habitId: Int) {
        repository.deleteHabit(habitId)
    }

}