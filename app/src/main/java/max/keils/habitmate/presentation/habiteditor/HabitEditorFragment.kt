package max.keils.habitmate.presentation.habiteditor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import max.keils.habitmate.HabitMateApp
import max.keils.habitmate.databinding.FragmentEditorHabitBinding
import max.keils.habitmate.presentation.ViewModelFactory
import javax.inject.Inject

class HabitEditorFragment : Fragment() {

    private var _binding: FragmentEditorHabitBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("_binding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HabitEditorViewModel::class.java]
    }

    private val args: HabitEditorFragmentArgs by navArgs()

    private val habitId by lazy { args.habitId }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as HabitMateApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditorHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isScreenModeIsEdit()) {
            viewModel.loadHabitById(habitId)
        }

        setupClickListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            shouldCloseScreen.observe(viewLifecycleOwner) { findNavController().popBackStack() }
            habitItem.observe(viewLifecycleOwner) {
                binding.etHabitName.setText(it.name)
                binding.etHabitDescription.setText(it.description)
            }
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            btnSave.setOnClickListener {
                val name = etHabitName.text.toString()
                val description =  etHabitDescription.text.toString()
                if (isScreenModeIsEdit())
                    viewModel.editHabit(name, description)
                else
                    viewModel.addHabit(name, description)
            }
        }
    }

    private fun isScreenModeIsEdit() = habitId != HABIT_ID_IS_ABSENT

    companion object {
        const val HABIT_ID_IS_ABSENT = -1
    }

}