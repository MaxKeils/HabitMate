package max.keils.domain.usecase

import max.keils.domain.repository.HabitRepository
import javax.inject.Inject

class GetHabitsUseCase @Inject constructor(private val repository: HabitRepository) {

    operator fun invoke() = repository.getHabits()

}