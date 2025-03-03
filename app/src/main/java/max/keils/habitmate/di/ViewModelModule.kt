package max.keils.habitmate.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import max.keils.habitmate.presentation.addhabit.AddHabitViewModel
import max.keils.habitmate.presentation.detailshabit.HabitDetailsViewModel
import max.keils.habitmate.presentation.habitlist.HabitListViewModel

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(HabitListViewModel::class)
    @Binds
    fun bindHabitsListViewModel(impl: HabitListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AddHabitViewModel::class)
    @Binds
    fun bindHabitEditorViewModel(impl: AddHabitViewModel): ViewModel

    @IntoMap
    @ViewModelKey(HabitDetailsViewModel::class)
    @Binds
    fun bindHabitDetailsViewModel(impl: HabitDetailsViewModel): ViewModel

}