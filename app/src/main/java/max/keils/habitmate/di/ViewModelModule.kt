package max.keils.habitmate.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import max.keils.habitmate.presentation.habitlist.HabitListViewModel

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(HabitListViewModel::class)
    @Binds
    fun bindHabitsListViewModel(impl: HabitListViewModel): ViewModel

}