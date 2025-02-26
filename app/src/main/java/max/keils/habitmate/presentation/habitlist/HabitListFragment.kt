package max.keils.habitmate.presentation.habitlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import max.keils.habitmate.HabitMateApp
import max.keils.habitmate.databinding.FragmentHabitListBinding
import max.keils.habitmate.presentation.ViewModelFactory
import max.keils.habitmate.presentation.habiteditor.HabitEditorFragment
import javax.inject.Inject

class HabitListFragment : Fragment() {

    private var _binding: FragmentHabitListBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("_binding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapter: HabitListAdapter

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HabitListViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as HabitMateApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeViewModel()
        setupClickListeners()
    }

    private fun setupClickListeners() {

        with(adapter) {
            onHabitItemClickListener = { habit ->
                HabitListFragmentDirections.actionHabitListFragmentToAddHabitFragment(habit.id)
                    .also { findNavController().navigate(it) }
            }
        }

        with(binding) {
            btnAddHabit.setOnClickListener {
                HabitListFragmentDirections.actionHabitListFragmentToAddHabitFragment(
                    HabitEditorFragment.HABIT_ID_IS_ABSENT
                ).also { findNavController().navigate(it) }
            }
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.habits
                    .collect { adapter.submitList(it) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        binding.recyclerView.adapter = adapter
    }
}