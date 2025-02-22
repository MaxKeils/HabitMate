package max.keils.domain.repository

import kotlinx.coroutines.flow.Flow
import max.keils.domain.model.Habit

interface HabitRepository {

    suspend fun addHabit(habit: Habit)

    suspend fun deleteHabit(habitId: Int)

    suspend fun updateHabit(habit: Habit)

    suspend fun getHabitById(habitId: Int): Habit?

    fun getHabits(): Flow<List<Habit>>

}