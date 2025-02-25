package max.keils.habitmate.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import max.keils.habitmate.presentation.habiteditor.HabitEditorFragment
import max.keils.habitmate.presentation.habiteditor.HabitEditorViewModel
import max.keils.habitmate.presentation.habitlist.HabitListViewModel

@Module
interface ViewModelModule {
    @IntoMap
    @ViewModelKey(HabitListViewModel::class)
    @Binds
    fun bindHabitsListViewModel(impl: HabitListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(HabitEditorViewModel::class)
    @Binds
    fun bindHabitEditorViewModel(impl: HabitEditorViewModel): ViewModel

}