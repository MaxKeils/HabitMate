package max.keils.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import max.keils.domain.model.Habit
import max.keils.domain.repository.HabitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepositoryImpl @Inject constructor() : HabitRepository {

    private val _habits = MutableStateFlow(emptyList<Habit>())
    private var counterId = 0

    override suspend fun addHabit(habit: Habit) {
        _habits.update { it + habit.copy(id = counterId++)}
    }

    override suspend fun deleteHabit(habitId: Int) {
        _habits.update { habits ->
            habits.filter { habit ->
                habit.id != habitId
            }
        }
    }

    override suspend fun updateHabit(newHabit: Habit) {
        _habits.update { habits ->
            habits.map { habit ->
                if (habit.id == newHabit.id)
                    newHabit
                else
                    habit
            }
        }
    }

    override suspend fun getHabitById(habitId: Int): Habit? =
        _habits.value.find { it.id == habitId }


    override fun getHabits(): Flow<List<Habit>> = _habits
}