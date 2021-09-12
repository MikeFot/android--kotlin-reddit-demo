package com.michaelfotiadis.demo.reddit.android.ui.activity.main.fragment.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.michaelfotiadis.demo.reddit.android.R
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.MainViewModel
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.MainUiState
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoadingFragment : Fragment() {

    private val navController: NavController by lazy {
        findNavController()
    }

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainViewModel.uiLiveData.observe(viewLifecycleOwner, { uiState ->
            when (uiState) {
                is MainUiState.Error -> goToErrorScreen()
                MainUiState.Success -> goToPostsScreen()
                MainUiState.Idle -> mainViewModel.getPosts()
                MainUiState.Loading -> {
                    // NOOP
                }
            }
        })

        return inflater.inflate(R.layout.fragment_loading, container, false)
    }

    private fun goToPostsScreen() {
        navController.navigate(R.id.action_loadingFragment_to_postsListFragment)
    }

    private fun goToErrorScreen() {
        navController.navigate(R.id.action_loadingFragment_to_errorFragment)
    }

}