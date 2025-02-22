package max.keils.domain.usecase

import kotlinx.coroutines.flow.Flow
import max.keils.domain.model.Habit
import max.keils.domain.repository.HabitRepository

class GetHabitsUseCase(private val repository: HabitRepository) {

    operator fun invoke(): Flow<List<Habit>> = repository.getHabits()

}