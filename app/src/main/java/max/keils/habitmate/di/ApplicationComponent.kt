package max.keils.habitmate.di

import dagger.Component
import max.keils.habitmate.presentation.habitslist.HabitsListFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: HabitsListFragment)

}