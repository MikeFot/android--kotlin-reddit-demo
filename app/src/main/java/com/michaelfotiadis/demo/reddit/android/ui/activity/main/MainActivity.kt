package com.michaelfotiadis.demo.reddit.android.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michaelfotiadis.demo.reddit.android.databinding.ActivityMainBinding
import com.michaelfotiadis.demo.reddit.android.ui.activity.main.model.MainUiState
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityRetainedScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope
import timber.log.Timber

class MainActivity : AppCompatActivity(), AndroidScopeComponent {

    override val scope: Scope by activityRetainedScope()

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        mainViewModel.uiLiveData.observe(this, { uiState ->
            when (uiState) {
                is MainUiState.Error -> Timber.d("Received Error")
                MainUiState.Idle -> Timber.d("Main screen is idle")
                MainUiState.Loading -> Timber.d("Loading...")
                MainUiState.Success -> Timber.d("Success - received items")
            }
        })
    }

}