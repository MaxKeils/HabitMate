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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AddHabitViewModel::class.java]
    }

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
        setupNavigation()
        setupClickListeners()
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
        }
    }

    private fun setupClickListeners() {
        binding.toolBar.setNavigationOnClickListener { showExitDialog() }

        requireActivity().onBackPressedDispatcher.addCallback {
            showExitDialog()
        }


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
        if (isScreenModeIsAdd())
            viewModel.addHabit(name, description)
        else
            viewModel.updateHabit(name, description)
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