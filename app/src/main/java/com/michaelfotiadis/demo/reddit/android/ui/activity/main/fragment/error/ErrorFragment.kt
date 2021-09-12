package com.michaelfotiadis.demo.reddit.android.ui.activity.main.fragment.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.michaelfotiadis.demo.reddit.android.R
import com.michaelfotiadis.demo.reddit.android.databinding.FragmentErrorBinding
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.MainViewModel
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.MainUiState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ErrorFragment : Fragment() {

    internal val navController: NavController by lazy {
        findNavController()
    }

    private lateinit var binding: FragmentErrorBinding
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_error, container, false)

        binding = FragmentErrorBinding.bind(view)

        binding.errorRetryButton.setOnClickListener {
            mainViewModel.clearError()
            goToLoadingScreen()
        }

        mainViewModel.uiLiveData.observe(viewLifecycleOwner, { uiState ->
            when (uiState) {
                is MainUiState.Error -> {
                    binding.errorText.setText(uiState.uiError.messageResId)
                    binding.errorRetryButton.setText(uiState.uiError.actionResId)
                }
                MainUiState.Idle -> {
                    // NOOP
                }
                MainUiState.Loading -> {
                    // NOOP
                }
                MainUiState.Success -> {
                    // NOOP
                }
            }
        })

        return view
    }

    private fun goToLoadingScreen() {
        navController.navigate(R.id.action_errorFragment_to_loadingFragment)
    }

}