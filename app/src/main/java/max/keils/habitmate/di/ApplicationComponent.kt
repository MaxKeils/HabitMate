package max.keils.habitmate.di

import dagger.Component
import max.keils.habitmate.presentation.addhabit.AddHabitFragment
import max.keils.habitmate.presentation.detailshabit.HabitDetailsFragment
import max.keils.habitmate.presentation.habitlist.HabitListFragment
import max.keils.habitmate.presentation.addhabit.ReminderBottomSheetDialogFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: HabitListFragment)

    fun inject(fragment: AddHabitFragment)

    fun inject(fragment: HabitDetailsFragment)

    fun inject(fragment: ReminderBottomSheetDialogFragment)

}