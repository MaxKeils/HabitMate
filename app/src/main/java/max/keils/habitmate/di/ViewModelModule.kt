package max.keils.habitmate.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import max.keils.habitmate.presentation.habitslist.HabitsListViewModel

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(HabitsListViewModel::class)
    @Binds
    fun bindHabitsListViewModel(impl: HabitsListViewModel): ViewModel

}