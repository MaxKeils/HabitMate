package max.keils.habitmate.presentation.addhabit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import max.keils.domain.model.ReminderTime
import max.keils.habitmate.HabitMateApp
import max.keils.habitmate.R
import max.keils.habitmate.databinding.FragmentReminderBottomSheetDialogBinding
import max.keils.habitmate.presentation.ViewModelFactory
import javax.inject.Inject

class ReminderBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentReminderBottomSheetDialogBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("_binding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val parentViewModel by lazy {
        ViewModelProvider(requireParentFragment(), viewModelFactory)[AddHabitViewModel::class.java]
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ReminderBottomSheetDialogViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as HabitMateApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReminderBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        observeViewModel()

        if (savedInstanceState == null) {
            arguments?.let {
                parentViewModel.getReminderById(it.getInt(REMINDER_ID))?.let { reminder ->
                    viewModel.setReminder(reminder)
                }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.reminder.observe(viewLifecycleOwner) {
            binding.tvTime.text = it.time.toFormattedString()
            (binding.chipGroup.getChildAt(it.daysOfWeek.numberDayOfTheWeek - 1) as Chip)
                .isChecked = true
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            btnSelectTime.setOnClickListener {
                val time = viewModel.reminder.value?.time ?: ReminderTime(
                    ReminderBottomSheetDialogViewModel.DEFAULT_HOUR,
                    ReminderBottomSheetDialogViewModel.DEFAULT_MINUTE
                )
                showTimePicker(time)
            }
            btnSaveReminder.setOnClickListener {
                viewModel.reminder.value?.let {
                    parentViewModel.addReminder(it.id, it.daysOfWeek, it.time)
                    dismiss()
                }
            }
            chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
                for (i in 0 until chipGroup.childCount) {
                    if (chipGroup.getChildAt(i).id in checkedIds) {
                        viewModel.updateSelectedDay(i + 1)
                        break
                    }
                }
            }
        }
    }

    private fun showTimePicker(time: ReminderTime) {
        MaterialTimePicker.Builder()
            .setTheme(R.style.ThemeOverlay_MyTimePicker)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(time.hour)
            .setMinute(time.minute)
            .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
            .build()
            .also { picker ->
                picker.addOnPositiveButtonClickListener {
                    viewModel.updateSelectedTime(picker.hour, picker.minute)
                }
                picker.show(childFragmentManager, TIME_PICKER_TAG)
            }
    }

    companion object {

        fun newInstance(reminderId: Int) =
            ReminderBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(REMINDER_ID, reminderId)
                }
            }

        fun newInstance() = ReminderBottomSheetDialogFragment()

        private const val REMINDER_ID = "reminderId"
        const val TIME_PICKER_TAG = "timePicker"
        const val TAG = "ReminderBottomSheetDialogFragment"
    }
}