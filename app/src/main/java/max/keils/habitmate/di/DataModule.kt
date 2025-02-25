package max.keils.habitmate.di

import dagger.Binds
import dagger.Module
import max.keils.data.repository.HabitRepositoryImpl
import max.keils.domain.repository.HabitRepository
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    @Singleton
    fun bindHabitRepository(impl: HabitRepositoryImpl): HabitRepository

}