package max.keils.habitmate.presentation.detailshabit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import max.keils.habitmate.HabitMateApp
import max.keils.habitmate.R
import max.keils.habitmate.databinding.FragmentHabitDetailsBinding
import max.keils.habitmate.presentation.ViewModelFactory
import javax.inject.Inject

class HabitDetailsFragment : Fragment() {

    private var _binding: FragmentHabitDetailsBinding? = null
    private val binding
        get() = _binding ?: throw RuntimeException("_binding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[HabitDetailsViewModel::class.java]
    }

    private val args: HabitDetailsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as HabitMateApp).component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHabitDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getHabitById(args.habitId)

        setupNavigation()
        setupClickListeners()
        observeViewModel()
        setupMenu()
    }


    private fun setupNavigation() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolBar)
    }

    private fun setupClickListeners() {
        binding.toolBar.setNavigationOnClickListener { finishWork() }

        requireActivity().onBackPressedDispatcher.addCallback {
            finishWork()
        }
    }

    private fun setupMenu() {
        requireActivity()
            .addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_habit_details, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.action_edit -> {
                            viewModel.habitLD.value?.let {
                                HabitDetailsFragmentDirections.actionEditHabitFragmentToAddHabitFragment(
                                    it.id
                                )
                            }?.let {
                                findNavController().navigate(it)
                            }
                            true
                        }

                        else -> false
                    }
                }

            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun finishWork() {
        findNavController().popBackStack()
    }


    private fun observeViewModel() {
        viewModel.habitLD.observe(viewLifecycleOwner) {
            with(binding) {
                toolBar.title = it.name
                tvDescription.text = it.description
            }
        }
    }

}