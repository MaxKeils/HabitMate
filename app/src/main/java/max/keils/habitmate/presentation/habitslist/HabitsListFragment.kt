package max.keils.habitmate.presentation.habitslist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import max.keils.domain.model.Habit
import max.keils.habitmate.HabitMateApp
import max.keils.habitmate.databinding.FragmentHabitsListBinding
import max.keils.habitmate.presentation.ViewModelFactory
import javax.inject.Inject

class HabitsListFragment : Fragment() {

    private var _binding: FragmentHabitsListBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("_binding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HabitsListViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as HabitMateApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.habits
                    .collect {
                        Log.d("HabitsListFragment", "Collected: $it")
                    }
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val testHabit = Habit(
                id = (0..1000).random(),
                name = "Test",
                description = "123",
                frequency = 1,
                isCompletedToday = true
            )
            viewModel.addHabit(testHabit)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}