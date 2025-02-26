package max.keils.habitmate.presentation.habitlist

import androidx.lifecycle.ViewModel
import max.keils.domain.usecase.GetHabitsUseCase
import javax.inject.Inject

class HabitListViewModel @Inject constructor(
    getHabitsUseCase: GetHabitsUseCase
) : ViewModel() {

    val habits = getHabitsUseCase()
}