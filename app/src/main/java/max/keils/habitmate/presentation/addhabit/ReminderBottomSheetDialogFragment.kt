package max.keils.habitmate.presentation.addhabit

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import max.keils.domain.model.Reminder
import max.keils.domain.model.ReminderTime
import max.keils.habitmate.HabitMateApp
import max.keils.habitmate.R
import max.keils.habitmate.databinding.FragmentReminderBottomSheetDialogBinding
import max.keils.habitmate.presentation.ViewModelFactory
import java.util.Locale
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
    }

    private fun observeViewModel() {
        viewModel.selectedTime.observe(viewLifecycleOwner) {
            val (hour, minute) = it
            binding.tvTime.text = String.format(Locale.ROOT, "%02d:%02d", hour, minute)
        }

        viewModel.selectedDay.observe(viewLifecycleOwner) {
            (binding.chipGroup.getChildAt(it.numberDayOfTheWeek - 1) as Chip).isChecked = true
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            btnSelectTime.setOnClickListener {
                val time = viewModel.selectedTime.value ?: ReminderTime(
                    ReminderBottomSheetDialogViewModel.DEFAULT_HOUR,
                    ReminderBottomSheetDialogViewModel.DEFAULT_MINUTE
                )
                showTimePicker(time)
            }
            btnSaveReminder.setOnClickListener {
                viewModel.selectedDay.value?.let { day ->
                    viewModel.selectedTime.value?.let { time ->
                        Log.d("observeViewModel", "$day | $time")
                        parentViewModel.addReminder(Reminder(daysOfWeek = day, time = time))
                        dismiss()
                    }
                }
            }
            chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
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
        const val TIME_PICKER_TAG = "timePicker"
        const val TAG = "ReminderBottomSheetDialogFragment"
    }
}