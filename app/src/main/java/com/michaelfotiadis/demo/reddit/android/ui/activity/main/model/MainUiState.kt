package com.michaelfotiadis.demo.reddit.android.ui.activity.main.model

import com.michaelfotiadis.demo.reddit.android.ui.activity.main.error.UiError

sealed class MainUiState {
    object Idle : MainUiState()
    object Loading : MainUiState()
    data class Error(val uiError: UiError) : MainUiState()
}