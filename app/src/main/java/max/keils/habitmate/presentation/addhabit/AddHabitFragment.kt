package max.keils.habitmate.presentation.addhabit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import max.keils.domain.model.Habit
import max.keils.habitmate.HabitMateApp
import max.keils.habitmate.R
import max.keils.habitmate.databinding.FragmentAddHabitBinding
import max.keils.habitmate.presentation.ViewModelFactory
import javax.inject.Inject

class AddHabitFragment : Fragment() {

    private var _binding: FragmentAddHabitBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("_binding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var adapter: ReminderListAdapter

    private val viewModel by viewModels<AddHabitViewModel> { viewModelFactory }

    private val args: AddHabitFragmentArgs by navArgs()
    private val habitId by lazy { args.habitId }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as HabitMateApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isScreenModeIsAdd()) {
            binding.toolBar.title = getString(R.string.edit_habit)
            viewModel.loadHabitById(habitId)
        }

        observeViewModel()
        setupRecyclerView()
        setupNavigation()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        binding.rcView.adapter = adapter

        setupSwipeListener(binding.rcView)
    }

    private fun setupSwipeListener(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.currentList[viewHolder.adapterPosition].let {
                    viewModel.removeReminder(it.id)
                }
            }

        }).attachToRecyclerView(recyclerView)
    }

    private fun observeViewModel() {
        with(viewModel) {
            shouldCloseScreen.observe(viewLifecycleOwner) { navigateBack() }
            habitItem.observe(viewLifecycleOwner) {
                with(binding) {
                    tiEtNameHabit.setText(it.name)
                    tiEtDescriptionHabit.setText(it.description)
                }
            }
            reminders.observe(viewLifecycleOwner) { adapter.submitList(it) }
        }
    }

    private fun setupClickListeners() {
        with(binding) {
            toolBar.setNavigationOnClickListener { showExitDialog() }

            tvAddReminder.setOnClickListener {
                childFragmentManager.findFragmentByTag(ReminderBottomSheetDialogFragment.TAG)
                    ?: ReminderBottomSheetDialogFragment().show(
                        childFragmentManager,
                        ReminderBottomSheetDialogFragment.TAG
                    )
            }
        }

        adapter.onEditHabitClickListener = {
            childFragmentManager.findFragmentByTag(ReminderBottomSheetDialogFragment.TAG)
                ?: ReminderBottomSheetDialogFragment.newInstance(it.id).show(
                    childFragmentManager,
                    ReminderBottomSheetDialogFragment.TAG
                )
        }

        requireActivity().onBackPressedDispatcher.addCallback { showExitDialog() }
    }

    private fun setupNavigation() {
        setupMenu()

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolBar)
    }

    private fun setupMenu() {
        requireActivity().apply {
            addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_add_habit, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_save -> {
                            clickListenerOnSaveButton()
                            true
                        }

                        else -> false
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }

    private fun clickListenerOnSaveButton() {
        val name = binding.tiEtNameHabit.text.toString()
        val description = binding.tiEtDescriptionHabit.text.toString()

        viewModel.saveHabit(name, description)
    }

    private fun showExitDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.warning))
            .setMessage(getString(R.string.dialog_warning_data_will_be_lost))
            .setPositiveButton(getString(R.string.exit)) { _, _ ->
                navigateBack()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
            .apply { setButtonColors(R.color.colorOnSurface) }
    }

    private fun AlertDialog.setButtonColors(@ColorRes colorRes: Int) {
        val color = ContextCompat.getColor(context, colorRes)
        getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(color)
        getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(color)
    }

    private fun navigateBack() {
        findNavController().popBackStack()
    }

    private fun isScreenModeIsAdd() = habitId == Habit.UNDEFINED_ID

}